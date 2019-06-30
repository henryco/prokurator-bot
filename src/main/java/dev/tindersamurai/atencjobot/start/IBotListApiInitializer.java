package dev.tindersamurai.atencjobot.start;

import net.dv8tion.jda.core.JDA;

import javax.security.auth.login.LoginException;
import java.util.Map;

public interface IBotListApiInitializer {

	JDA createBotApi() throws LoginException;

	String createPrefix();

	String[] createErrorMsg();

	Map<String, String[]> createResponse();

	Map<String, String[]> createAlias();
}
