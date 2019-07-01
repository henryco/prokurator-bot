package dev.tindersamurai.atencjobot.bot;

import lombok.val;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.managers.GuildController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import java.util.Random;

public abstract class ProkuratorBotCommandListener extends ProkuratorBotEventListener {

	private String[] error;
	private String prefix;

	protected abstract void onCommand(MessageReceivedEvent event, GuildController controller);

	public abstract String commandName();

	@SuppressWarnings("WeakerAccess")
	public String[] commandAlias() {
		return new String[0];
	}

	@SuppressWarnings("WeakerAccess")
	protected boolean caseSensitive() {
		return false;
	}

	@SuppressWarnings("WeakerAccess")
	protected Permission[] requiredUserPermissions() {
		return new Permission[0];
	}

	@SuppressWarnings("WeakerAccess")
	protected Permission[] requiredBotPermissions() {
		return new Permission[0];
	}

	@Autowired @Qualifier("prefix")
	public final void setCommandPrefix(@NonNull String prefix) {
		this.prefix = prefix;
	}

	@Autowired @Qualifier("error")
	public final void setErrorMsg(@NonNull String[] errorMsg) {
		this.error = errorMsg;
	}

	@Override
	public final void onMessageReceived(MessageReceivedEvent event) {
		if (event.getAuthor().isBot()) return;
		if (!resolveCommand(event.getMessage().getContentDisplay())) return;

		userHasRequiredPermissions(event);
		hasRequiredPermissions(event);

		try {
			onCommand(event, new GuildController(event.getGuild()));
		} catch (Exception e) {
			sendErrorMsg(event, e);
			throw e;
		}
	}

	private void userHasRequiredPermissions(MessageReceivedEvent event) {
		val required = requiredUserPermissions();
		if (required == null || required.length == 0) return;

		if (!event.getGuild().getMember(event.getAuthor()).hasPermission(required)) {
			throw new RuntimeException("USER ROLE REQUIRED!");
		}
	}

	private void hasRequiredPermissions(MessageReceivedEvent event) {
		val required = requiredBotPermissions();
		if (required == null || required.length == 0) return;

		val permissions = event.getGuild().getSelfMember().getPermissions();
		for (val p: required) {
			if (!permissions.contains(p))
				throw new RuntimeException("BOT ROLE: " + p + " REQUIRED!");
		}
	}

	private boolean resolveCommand(String input) {
		val content = cased(input, caseSensitive()).trim();
		val names = commandAlias();
		if (names != null) {
			for (val name: names) {
				if (content.startsWith(prefix + cased(name, caseSensitive()).trim()))
					return true;
			}
		}
		return content.startsWith(prefix + cased(commandName(), caseSensitive()).trim());
	}

	private void sendErrorMsg(MessageReceivedEvent event, Exception e) {
		if (error == null || error.length == 0) return;
		val coreMsg = error[new Random().nextInt(error.length)];
		val reason = "```\n" + e.getMessage() + "\n```";

		event.getChannel().sendMessage(coreMsg + "\n" + reason).queue();
	}


	private static String cased(String value, boolean sensitive) {
		return sensitive ? value : value.toLowerCase();
	}
}
