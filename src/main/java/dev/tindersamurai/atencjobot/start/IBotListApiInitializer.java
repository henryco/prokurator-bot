package dev.tindersamurai.atencjobot.start;

import net.dv8tion.jda.api.JDA;

import javax.security.auth.login.LoginException;

public interface IBotListApiInitializer {

	JDA createBotApi() throws LoginException;
}