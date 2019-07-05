package dev.tindersamurai.atencjobot.mvc.service.storage;

import org.springframework.lang.NonNull;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

public interface FileStorageService {

	OutputStream getFileStream(@NonNull String fid);

	byte[] getFileBytes(@NonNull String fid);

	File getFile(@NonNull String fid);

	/** @return UID string */
	String storeFile(@NonNull File file);

	/** @return UID string */
	default String storeFile(@NonNull byte[] bytes) {
		return storeFile(new ByteArrayInputStream(bytes));
	}

	/** @return UID string */
	String storeFile(@NonNull InputStream stream);
}
