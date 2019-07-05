package dev.tindersamurai.atencjobot.mvc.service.storage;

import lombok.Builder;
import lombok.Value;
import org.springframework.lang.NonNull;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

public interface FileStorageService {

	@Value @Builder class Metadata {
		private @NonNull String id;
		private @NonNull Date date;
		private @NonNull String name;
		private @NonNull String channelId;
		private @NonNull String authorId;
		private @NonNull boolean image;
		private @NonNull int size;
	}

	OutputStream getFileStream(String id);

	byte[] getFileBytes(String id);

	File getFile(String id);

	void storeFile(Metadata metadata, File file);

	void storeFile(Metadata metadata, byte[] bytes);

	void storeFile(Metadata metadata, InputStream stream);
}
