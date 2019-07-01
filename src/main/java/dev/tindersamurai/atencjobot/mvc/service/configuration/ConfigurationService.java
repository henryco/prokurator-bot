package dev.tindersamurai.atencjobot.mvc.service.configuration;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public interface ConfigurationService {

	<T> void addConfigParam(@NonNull String name, @NonNull Class<? extends T> type, @Nullable T value);

	default <T> void addConfigParam(@NonNull String name, @NonNull Class<? extends T> type) {
		this.addConfigParam(name, type, null);
	}

	<T> void set(@NonNull String name, @Nullable T value);

	<T> T get(@NonNull String name, @NonNull T defaultValue);

	<T> T get(@NonNull String name);

	boolean isPropExists(@NonNull String name);
}
