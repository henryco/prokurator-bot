package dev.tindersamurai.atencjobot;

import dev.tindersamurai.atencjobot.start.IBotListApiInitializer;
import net.dv8tion.jda.api.JDA;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.security.auth.login.LoginException;

@SpringBootApplication
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

}
