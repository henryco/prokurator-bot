package dev.tindersamurai.atencjobot.mvc.service.configuration;

import lombok.val;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public interface ConfigurationService {

	<T> void addConfigParam(@NonNull String name, @NonNull Class<? extends T> type, @Nullable T value);

	default <T> void addConfigParam(@NonNull String name, @NonNull Class<? extends T> type) {
		this.addConfigParam(name, type, null);
	}

	void removeConfigParam(@NonNull String name);

	<T> void set(@NonNull String name, @Nullable T value);

	default <T> T get(@NonNull String name, @NonNull T defaultValue) {
		T o = this.get(name);
		return o == null ? defaultValue : o;
	}

	<T> T get(@NonNull String name);

	boolean isPropExists(@NonNull String name);
}
