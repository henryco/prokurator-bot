package dev.tindersamurai.prokurator.mvc.service.storage.lfs;

import dev.tindersamurai.prokurator.mvc.service.storage.FileStorageService;
import dev.tindersamurai.prokurator.mvc.service.storage.lfs.data.LFSEntity;
import dev.tindersamurai.prokurator.mvc.service.storage.lfs.data.LFSRepository;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service @Slf4j
public class LFSFileStorageService implements FileStorageService {

	private static final String WORK_DIR = "resources/lfs";

	private final LFSRepository lfsRepository;

	@Autowired
	public LFSFileStorageService(LFSRepository lfsRepository) {
		this.lfsRepository = lfsRepository;
	}

	private String persist(String path) {
		log.debug("persist({})", path);
		val file = new LFSEntity(); {
			file.setId(UUID.randomUUID().toString());
			file.setPath(path);
			lfsRepository.save(file);
		}
		return file.getId();
	}

	@Transactional
	protected String getFilePath(@NonNull String fid)
			throws FileNotFoundException {
		log.debug("getFilePath({})", fid);
		val one = lfsRepository.findById(fid);
		if (!one.isPresent())
			throw new FileNotFoundException(fid);
		return one.get().getPath();
	}

	@Override
	public OutputStream getFileStream(@NonNull String fid) throws FileNotFoundException {
		log.debug("getFileStream({})", fid);
		return new FileOutputStream(new File(getFilePath(fid)));
	}

	@Override
	public byte[] getFileBytes(@NonNull String fid) throws FileNotFoundException {
		log.debug("getFileBytes({})", fid);
		val file = getFile(fid);
		val length = file.length();
		val bytes = new byte[(int) length];

		try {
			@Cleanup val stream = new FileOutputStream(file);
			stream.write(bytes);
			return bytes;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public File getFile(@NonNull String fid) throws FileNotFoundException {
		log.debug("getFile({})", fid);
		return new File(getFilePath(fid));
	}

	@Override
	public String storeFile(@NonNull File file) {
		log.debug("storeFile({})", file);
		return persist(file.getAbsolutePath());
	}

	@Override
	public String storeFile(@NonNull InputStream stream, @Nullable String name) {
		log.debug("storeFile({}, {})", stream, name);
		val fileName = name == null || name.isEmpty() ? UUID.randomUUID().toString() : name;
		return persist(writeStreamToFile(stream, fileName));
	}


	/** @return Path to saved file */
	private static String writeStreamToFile(@NonNull InputStream in, @NonNull String file) {
		log.debug("writeStreamToFile({}, {})", in, file);
		try {
			val path = Paths.get(WORK_DIR, file);
			//noinspection ResultOfMethodCallIgnored
			new File(path.getParent().toUri()).mkdirs();

			val f = path.toFile();
			if (f.exists()) {
				log.debug("File: {} already exists, create new name...", file);
				val uniqueOne = UUID.randomUUID().toString() + "-" + f.getName();
				log.debug("New unique file name: {}", uniqueOne);
				val uniquePath = Paths.get(path.getParent().toString(), uniqueOne);

				Files.copy(in, uniquePath);
				return uniquePath.toString();
			}

			Files.copy(in, path);
			return path.toString();
		} catch (IOException e) {
			log.error("Cannot save file", e);
			return null;
		}
	}
}
