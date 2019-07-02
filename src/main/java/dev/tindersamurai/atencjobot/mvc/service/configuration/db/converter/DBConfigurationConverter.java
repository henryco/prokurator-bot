package dev.tindersamurai.atencjobot.mvc.service.configuration.db.converter;

import lombok.val;

import java.util.Objects;

public interface DBConfigurationConverter {

	Class<?>[] getConvertibleClasses();

	default String toString(Object o) {
		if (o != null) {
			for (val c: getConvertibleClasses()) {
				if (o.getClass().equals(c))
					return Objects.toString(o);
			}
		}
		throw new RuntimeException("INVALID CONVERTER TYPE ARGUMENT");
	}

	<T> T fromString(String o, Class<? extends T> type);
}
