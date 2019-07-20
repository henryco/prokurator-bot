package dev.tindersamurai.prokurator;

import com.google.common.io.BaseEncoding;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;

@Slf4j
public class Hash {

	private static byte[] calcMD5(String p) {
		try {
			val path = Paths.get(p);
			if (!path.toFile().exists()) return new byte[0];
			return MessageDigest.getInstance("MD5").digest(Files.readAllBytes(path));
		} catch (Exception e) {
			log.error("Cannot calculate md5 sum", e);
			return new byte[0];
		}
	}

	@Test
	public void calculateTargetMD5Hash() {
		val currentMD5 = BaseEncoding.base16().encode(calcMD5("target/prokurator-0.0.1-SNAPSHOT.jar"));
		log.info("target hash: {}", currentMD5);
	}

}
