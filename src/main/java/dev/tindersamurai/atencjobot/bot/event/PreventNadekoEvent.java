package dev.tindersamurai.atencjobot.bot.event;

import dev.tindersamurai.atencjobot.bot.AtencjoBotEventListener;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Component;

@Component @Slf4j
public class PreventNadekoEvent extends AtencjoBotEventListener {

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {

		if (!event.isFromType(ChannelType.TEXT)) return;
		if (!event.getAuthor().isBot()) return;
		if (event.getMember().getRoles().stream().noneMatch(r -> "Nadeko".equals(r.getName()))) return;

		val channel = event.getChannel();
		channel.sendMessage("Nadeko ty kurwo jebana!").queue();
	}
}