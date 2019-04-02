package dev.tindersamurai.atencjobot.start;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.springframework.stereotype.Component;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;

@Component @Slf4j
public class BotListApiFileInitializer implements IBotListApiInitializer {

	@Data @NoArgsConstructor @AllArgsConstructor
	private static final class JsonForm {
		private Map<String, String[]> response;
		private Map<String, String[]> alias;
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
	public Map<String, String[]> createResponse() {
		if (form.response == null) {
			log.info("No custom response found");
			return Collections.emptyMap();
		}
		return Collections.unmodifiableMap(form.response);
	}

	@Override
	public Map<String, String[]> createAlias() {
		if (form.alias == null) {
			log.info("No custom alias found");
			return Collections.emptyMap();
		}
		return Collections.unmodifiableMap(form.alias);
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