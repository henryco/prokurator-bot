package dev.tindersamurai.atencjobot.bot.command;

import dev.tindersamurai.atencjobot.bot.ProkuratorBotCommandListener;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.managers.GuildController;
import org.springframework.stereotype.Component;

@Component
public class PingPongCommand extends ProkuratorBotCommandListener {

	@Override
	protected void onCommand(MessageReceivedEvent event, GuildController controller) {
		event.getChannel().sendMessage("Teczka rośnie...").queue();
	}

	@Override
	public String commandName() {
		return "status";
	}
}
