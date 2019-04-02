package dev.tindersamurai.atencjobot.bot;

import lombok.val;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;

import java.util.Map;
import java.util.Random;

public abstract class AtencjoBotCommandListener extends AtencjoBotEventListener {

	private Map<String, String[]> response;
	private Map<String, String[]> alias;
	private String prefix;

	public abstract void onCommand(MessageReceivedEvent event);

	public abstract String getCommandName();

	@SuppressWarnings("WeakerAccess")
	protected boolean caseSensitive() {
		return false;
	}

	@Autowired @Qualifier("prefix")
	public final void setCommandPrefix(@NonNull String prefix) {
		this.prefix = prefix;
	}

	@Autowired @Qualifier("alias")
	public final void setAlias(@NonNull Map<String, String[]> alias) {
		this.alias = alias;
	}

	@Autowired @Qualifier("response")
	public final void setResponse(@NonNull Map<String, String[]> response) {
		this.response = response;
	}

	@Override
	public final void onMessageReceived(MessageReceivedEvent event) {
		if (!resolveCommand(event.getMessage().getContentDisplay())) return;
		if (event.getAuthor().isBot()) return;
		onCommand(event);
		response(event);
	}

	private void response(MessageReceivedEvent event) {
		val res = response.get(getCommandName());
		if (res == null) return;
		event.getChannel().sendMessage(res[new Random().nextInt(res.length)]).queue();
	}

	private boolean resolveCommand(String input) {
		val content = cased(input, caseSensitive()).trim();
		val names = alias.get(getCommandName());
		if (names != null) {
			for (val name: names) {
				if (content.startsWith(prefix + cased(name, caseSensitive()).trim()))
					return true;
			}
		}
		return content.startsWith(prefix + cased(getCommandName(), caseSensitive()).trim());
	}

	private static String cased(String value, boolean sensitive) {
		return sensitive ? value : value.toLowerCase();
	}
}