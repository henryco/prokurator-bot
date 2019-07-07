package dev.tindersamurai.prokurator.mvc.service.configuration.db.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component @Slf4j
public class PrimitiveConfigConverter implements DBConfigurationConverter {

	@Override
	public Class<?>[] getConvertibleClasses() {
		return new Class[] {
				String.class,
				Float.class,
				Integer.class,
				Long.class,
				Double.class,
				Boolean.class,
				Short.class
		};
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T fromString(String o, Class<? extends T> type) {
		if (o == null) return null;
		if (type.equals(String.class))
			return (T) o;
		if (type.equals(Float.class))
			return (T) new Float(o);
		if (type.equals(Integer.class))
			return (T) new Integer(o);
		if (type.equals(Long.class))
			return (T) new Long(o);
		if (type.equals(Double.class))
			return (T) new Double(o);
		if (type.equals(Boolean.class))
			return (T) Boolean.valueOf(o);
		if (type.equals(Short.class))
			return (T) new Short(o);
		throw new RuntimeException("INVALID CONVERTER TYPE ARGUMENT");
	}
}
