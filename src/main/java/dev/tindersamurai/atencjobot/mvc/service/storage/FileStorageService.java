package dev.tindersamurai.atencjobot.mvc.service.storage;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

public interface FileStorageService {

	OutputStream getFileStream(String id);

	byte[] getFileBytes(String id);

	File getFile(String id);

	void storeFile(String id, File file);

	void storeFile(String id, InputStream stream);

	void storeFile(String id, byte[] bytes);
}
