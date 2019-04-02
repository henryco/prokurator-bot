package dev.tindersamurai.atencjobot;

import dev.tindersamurai.atencjobot.start.IBotListApiInitializer;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.security.auth.login.LoginException;
import java.util.Map;

@SpringBootApplication @Slf4j
public class AtencjoBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(AtencjoBotApplication.class, args);
	}

	@Bean
	public JDA provideApi(IBotListApiInitializer initializer) {
		try {
			return initializer.createBotApi();
		} catch (LoginException e) {
			System.exit(42);
			throw new RuntimeException("Login exception", e);
		}
	}

	@Bean("alias")
	public Map<String, String[]> provideAlias(IBotListApiInitializer initializer) {
		return initializer.createAlias();
	}

	@Bean("response")
	public Map<String, String[]> provideResponse(IBotListApiInitializer initializer) {
		return initializer.createResponse();
	}

	@Bean("error")
	public String[] provideErrorMessage(IBotListApiInitializer initializer) {
		try {
			return initializer.createErrorMsg();
		} catch (NullPointerException e) {
			log.warn("Cannot recognize default error message!");
			return new String[0];
		}
	}

	@Bean("prefix")
	public String provideCommandPrefix(IBotListApiInitializer initializer) {
		try {
			return initializer.createPrefix();
		} catch (NullPointerException e) {
			log.warn("Cannot recognize default command prefix, [xd!] used as default!");
			return "xd!";
		}
	}

}
