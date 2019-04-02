package dev.tindersamurai.atencjobot.bot;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public abstract class AtencjoBotEventListener extends ListenerAdapter {

	@Autowired
	public final void register(JDA jda) {
		val clazz = this.getClass();
		if (jda.getRegisteredListeners().stream().noneMatch(o -> o.getClass().equals(clazz))) {
			jda.addEventListener(this);
			log.debug("Registered event listener: {}", this);
		}
	}



}