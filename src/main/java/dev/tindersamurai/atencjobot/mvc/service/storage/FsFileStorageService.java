package dev.tindersamurai.atencjobot.mvc.service.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

@Service @Slf4j
public class FsFileStorageService implements FileStorageService {


	@Override
	public OutputStream getFileStream(String id) {
		return null;
	}

	@Override
	public byte[] getFileBytes(String id) {
		return new byte[0];
	}

	@Override
	public File getFile(String id) {
		return null;
	}

	@Override
	public void storeFile(Metadata metadata, File file) {

	}

	@Override
	public void storeFile(Metadata metadata, byte[] bytes) {

	}

	@Override
	public void storeFile(Metadata metadata, InputStream stream) {

	}
}
