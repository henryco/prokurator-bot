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

@Component @Slf4j
public class BotListApiFileInitializer implements IBotListApiInitializer {

	@Data @NoArgsConstructor @AllArgsConstructor
	private static final class JsonForm {
		private String token;
	}

	@Override
	public JDA createBotApi() throws LoginException {
		val form = parseFile("config.json");
		log.info("Starting bot, TOKEN: {}", form.token);
		return new JDABuilder(form.token).build();
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