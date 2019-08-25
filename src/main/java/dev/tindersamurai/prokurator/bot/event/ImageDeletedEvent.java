package dev.tindersamurai.prokurator.bot.event;

import dev.tindersamurai.prokurator.backend.commons.service.IMediaService;
import dev.tindersamurai.prokurator.bot.ProkuratorBotEventListener;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.dv8tion.jda.core.events.message.MessageDeleteEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component @Slf4j
public class ImageDeletedEvent extends ProkuratorBotEventListener {

	private final IMediaService mediaService;

	@Autowired
	public ImageDeletedEvent(IMediaService mediaService) {
		this.mediaService = mediaService;
	}

	@Override
	public void onMessageDelete(MessageDeleteEvent event) {
		log.debug("image delete event: {}", event);
		val messageId = event.getMessageId();

		log.debug("MESSAGE DELETED: {}", messageId);
		mediaService.removeMedia(messageId);
	}
}
