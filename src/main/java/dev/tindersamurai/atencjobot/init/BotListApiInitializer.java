package dev.tindersamurai.atencjobot.init;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.security.auth.login.LoginException;

@Component @Slf4j @PropertySource(value = "classpath:/token.properties")
public class BotListApiInitializer implements IBotListApiInitializer {

	private final Environment environment;

	@Autowired
	public BotListApiInitializer(Environment env) {
		this.environment = env;
	}

	@Override
	public JDA createBotApi() throws LoginException {
		log.info("createBotApi()");
		val token = environment.getRequiredProperty("token");
		return new JDABuilder(token).build();
	}
}
