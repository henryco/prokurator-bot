package dev.tindersamurai.atencjobot.mvc.service.storage.lfs;

import dev.tindersamurai.atencjobot.mvc.service.storage.FileStorageService;
import dev.tindersamurai.atencjobot.mvc.service.storage.lfs.data.LFSEntity;
import dev.tindersamurai.atencjobot.mvc.service.storage.lfs.data.LFSRepository;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

@Service @Slf4j
public class LFSFileStorageService implements FileStorageService {

	private final LFSRepository lfsRepository;

	@Autowired
	public LFSFileStorageService(LFSRepository lfsRepository) {
		this.lfsRepository = lfsRepository;
	}

	private String persist(@NonNull String path) {
		val file = new LFSEntity(); {
			file.setId(UUID.randomUUID().toString());
			file.setPath(path);
			lfsRepository.save(file);
		}
		return file.getId();
	}

	@Transactional
	protected String getFilePath(@NonNull String fid) {
		return lfsRepository.getOne(fid).getPath();
	}

	@Override
	public OutputStream getFileStream(@NonNull String fid) {
		val filePath = getFilePath(fid);
		return null;
	}

	@Override
	public byte[] getFileBytes(@NonNull String fid) {
		val filePath = getFilePath(fid);
		return new byte[0];
	}

	@Override
	public File getFile(@NonNull String fid) {
		val filePath = getFilePath(fid);
		return null;
	}

	@Override
	public String storeFile(@NonNull File file) {

		return persist("");
	}

	@Override
	public String storeFile(@NonNull InputStream stream) {
		return persist("");
	}
}
