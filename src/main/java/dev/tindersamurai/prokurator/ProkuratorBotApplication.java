package dev.tindersamurai.prokurator;

import dev.tindersamurai.prokurator.init.IBotListApiInitializer;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.h2.tools.Server;
import net.dv8tion.jda.core.JDA;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

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

	@Bean @Profile("dev")
	public Server h2Server() {
		val server = new Server();
		try {
			server.runTool("-tcp");
			server.runTool("-tcpAllowOthers");
		} catch (Exception e) {
			log.error("Cannot setup h2 server", e);
		}
		return server;
	}

	@Bean
	public CommonsRequestLoggingFilter requestLoggingFilter() {
		val loggingFilter = new CommonsRequestLoggingFilter(); {
			loggingFilter.setIncludeClientInfo(true);
			loggingFilter.setIncludeQueryString(true);
			loggingFilter.setIncludePayload(true);
			loggingFilter.setIncludeHeaders(true);
		}
		return loggingFilter;
	}

}
