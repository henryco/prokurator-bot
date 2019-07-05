package dev.tindersamurai.atencjobot.bot.event;

import dev.tindersamurai.atencjobot.bot.ProkuratorBotEventListener;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Component;

@Component @Slf4j
public class PreventNadekoEvent extends ProkuratorBotEventListener {

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		log.debug("nadeko event");
		if (!event.isFromType(ChannelType.TEXT)) return;
		if (!event.getAuthor().isBot()) return;
		if (event.getMember().getRoles().stream().noneMatch(r -> "Nadeko".equals(r.getName()))) return;

		val channel = event.getChannel();
		channel.sendMessage("Remove nadeko").queue();
	}
}
