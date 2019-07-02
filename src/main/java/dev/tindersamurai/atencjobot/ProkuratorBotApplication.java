package dev.tindersamurai.atencjobot;

import dev.tindersamurai.atencjobot.init.IBotListApiInitializer;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.core.JDA;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.security.auth.login.LoginException;

@SpringBootApplication @Slf4j
public class ProkuratorBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProkuratorBotApplication.class, args);
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

}
