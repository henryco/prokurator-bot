package dev.tindersamurai.atencjobot.start;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import org.springframework.stereotype.Component;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

@Component @Slf4j
public class BotListApiFileInitializer implements IBotListApiInitializer {

	@Data @NoArgsConstructor @AllArgsConstructor
	private static final class JsonForm {
		private String[] error;
		private String prefix;
		private String token;
	}

	private final JsonForm form;
	public BotListApiFileInitializer() {
		this.form = parseFile("config.json");
	}

	@Override
	public JDA createBotApi() throws LoginException {
		log.info("Starting bot, TOKEN: {}", form.token);
		return new JDABuilder(form.token).build();
	}

	@Override
	public String createPrefix() {
		log.info("Command prefix: {}", form.prefix);
		if (form.prefix == null)
			throw new NullPointerException("Prefix cannot be null");
		return form.prefix;
	}

	@Override
	public String[] createErrorMsg() {
		log.info("Error msg: {}", Arrays.toString(form.error));
		if (form.error == null)
			throw new NullPointerException("There are no error messages");
		return form.error;
	}

	@SuppressWarnings("SameParameterValue")
	private static JsonForm parseFile (String file) {
		try {
			val bytes = Files.readAllBytes(Paths.get(file));
			return new ObjectMapper().readValue(bytes, JsonForm.class);
		} catch (IOException e) {
			log.error("Cannot read config file", e);
			throw new RuntimeException("Cannot read config file", e);
		}
	}
}
