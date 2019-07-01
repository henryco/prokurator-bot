package dev.tindersamurai.atencjobot.mvc.service.configuration;

import dev.tindersamurai.atencjobot.mvc.data.dao.jpa.ConfigurationRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @Slf4j
public class DBConfigurationService implements ConfigurationService {

	private final ConfigurationRepo configurationRepo;

	@Autowired
	public DBConfigurationService(ConfigurationRepo configurationRepo) {
		this.configurationRepo = configurationRepo;
	}

	@Override @Transactional
	public <T> void addConfigParam(@NonNull String name, @NonNull Class<? extends T> type, T value) {
		
	}

	@Override
	public <T> void set(@NonNull String name, T value) {

	}

	@Override
	public <T> T get(@NonNull String name, @NonNull T defaultValue) {
		return null;
	}

	@Override
	public <T> T get(@NonNull String name) {
		return null;
	}

	@Override
	public boolean isPropExists(@NonNull String name) {
		return configurationRepo.existsById(name);
	}
}
