package dev.tindersamurai.atencjobot.mvc.service.storage;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.io.*;

public interface FileStorageService {

	OutputStream getFileStream(@NonNull String fid) throws FileNotFoundException;

	byte[] getFileBytes(@NonNull String fid) throws FileNotFoundException;

	File getFile(@NonNull String fid) throws FileNotFoundException;

	/** @return UID string */
	String storeFile(@NonNull File file);

	/** @return UID string */
	String storeFile(@NonNull InputStream stream, @Nullable String name);

	/** @return UID string */
	default String storeFile(@NonNull InputStream stream) {
		return storeFile(stream, null);
	}

	/** @return UID string */
	default String storeFile(@NonNull byte[] bytes, @Nullable String name) {
		return storeFile(new ByteArrayInputStream(bytes), name);
	}

	/** @return UID string */
	default String storeFile(@NonNull byte[] bytes) {
		return storeFile(bytes, null);
	}
}
