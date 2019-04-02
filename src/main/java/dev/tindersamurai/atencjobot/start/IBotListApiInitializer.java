package dev.tindersamurai.atencjobot.start;

import net.dv8tion.jda.api.JDA;

import javax.security.auth.login.LoginException;
import java.util.Map;

public interface IBotListApiInitializer {

	JDA createBotApi() throws LoginException;

	String createPrefix();

	Map<String, String[]> createResponse();

	Map<String, String[]> createAlias();
}