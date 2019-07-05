package dev.tindersamurai.atencjobot;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.Test;

import java.nio.file.Paths;

@Slf4j
public class FilesTests {

	@Test
	public void test() {
		val someFile = "some/File";
		val p1 = Paths.get(someFile, "other");
		log.debug("path: {}", p1);

		log.debug("pp: {}", p1.getFileName());
		log.debug("ppp: {}", p1.getParent().toString());

		val single = Paths.get("single");
		log.debug("single: {}", single);
		log.debug("n: {}", single.getFileName());
		log.debug("nn: {}", single.getParent());
	}
}
