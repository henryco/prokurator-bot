package dev.tindersamurai.prokurator.init;

import net.dv8tion.jda.core.JDA;

import javax.security.auth.login.LoginException;

public interface IBotListApiInitializer {

	JDA createBotApi() throws LoginException;
}
