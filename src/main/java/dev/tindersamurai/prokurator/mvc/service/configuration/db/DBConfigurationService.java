package dev.tindersamurai.prokurator.mvc.service.configuration.db;

import dev.tindersamurai.prokurator.mvc.data.dao.jpa.ConfigurationRepo;
import dev.tindersamurai.prokurator.mvc.data.entity.Configuration;
import dev.tindersamurai.prokurator.mvc.service.configuration.ConfigurationService;
import dev.tindersamurai.prokurator.mvc.service.configuration.db.converter.DBConfigurationConverter;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;


@Service @Slf4j
public class DBConfigurationService implements ConfigurationService {

	private final Map<Class<?>, DBConfigurationConverter> converterMap;
	private final ConfigurationRepo configurationRepo;

	@Autowired
	public DBConfigurationService(
			ConfigurationRepo configurationRepo,
			DBConfigurationConverter[] converters
	) {
		this.converterMap = registerConverters(converters);
		this.configurationRepo = configurationRepo;
	}

	@Override @Transactional
	public <T> void addConfigParam(@NonNull String name, @NonNull Class<? extends T> type, T value) {
		val param = new Configuration(); {
			param.setName(name);
			param.setType(type);
			if (value != null)
				param.setValue(converterMap.get(type).toString(value));
		}
		configurationRepo.save(param);
	}

	@Override
	public void removeConfigParam(@NonNull String name) {
		configurationRepo.deleteById(name);
	}

	@Override @Transactional
	public <T> void set(@NonNull String name, T value) {
		val one = configurationRepo.getOne(name);
		one.setValue(converterMap.get(one.getType()).toString(value));
		configurationRepo.save(one);
	}

	@Override @Transactional
	public <T> T get(@NonNull String name) {
		val found = configurationRepo.findById(name);
		if (!found.isPresent()) return null;
		val one = found.get();

		//noinspection unchecked
		return (T) converterMap.get(one.getType()).fromString(one.getValue(), one.getType());
	}

	@Override
	public boolean isPropExists(@NonNull String name) {
		return configurationRepo.existsById(name);
	}

	private static Map<Class<?>, DBConfigurationConverter>
	registerConverters(DBConfigurationConverter[] converters) {
		val map = new HashMap<Class<?>, DBConfigurationConverter>();
		for (val converter: converters) {
			for (val c: converter.getConvertibleClasses()) {
				map.put(c, converter);
			}
		}
		return map;
	}
}
