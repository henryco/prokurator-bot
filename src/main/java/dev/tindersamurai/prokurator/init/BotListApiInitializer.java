package dev.tindersamurai.prokurator.init;

import dev.tindersamurai.prokurator.bot.collector.IBotEventCollector;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.security.auth.login.LoginException;

@Component @Slf4j
public class BotListApiInitializer implements IBotListApiInitializer {

	private final IBotEventCollector eventCollector;
	private final Environment environment;

	@Autowired
	public BotListApiInitializer(
			IBotEventCollector eventCollector,
			Environment env
	) {
		this.eventCollector = eventCollector;
		this.environment = env;
	}

	@Override
	public JDA createBotApi() throws LoginException {
		log.info("createBotApi()");
		val token = environment.getRequiredProperty("prokurator.bot.token");
		val jda = new JDABuilder(token)
				.setStatus(OnlineStatus.INVISIBLE)
				.build();
		jda.addEventListener(eventCollector);
		return jda;
	}
}
