package dev.tindersamurai.atencjobot.bot.event;

import dev.tindersamurai.atencjobot.bot.ProkuratorBotEventListener;
import dev.tindersamurai.atencjobot.mvc.service.storage.FileStorageService;
import dev.tindersamurai.atencjobot.mvc.service.storage.FileStorageService.Metadata;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.Message.Attachment;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component @Slf4j
public class ImagePostedEvent extends ProkuratorBotEventListener {

	private final FileStorageService fileStorageService;

	@Autowired
	public ImagePostedEvent(FileStorageService fileStorageService) {
		this.fileStorageService = fileStorageService;
	}

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		log.info("image post event: {}", event);
		val message = event.getMessage();
		val attachments = message.getAttachments();
		if (attachments == null || attachments.isEmpty())
			return;

		attachments.forEach(a -> processAttachment(message, a));
	}

	private void processAttachment(Message m, Attachment a) {
		val fileName = a.getFileName();
		val image = a.isImage();
		val size = a.getSize();
		val url = a.getUrl();

		log.info("---ATTACHMENT---");
		log.info("NAME: {}", fileName);
		log.info("IMAGE: {}", image);
		log.info("SIZE: {}", size);
		log.info("URL: {}", url);

		val metadata = Metadata.builder()
				.authorId(m.getAuthor().getId())
				.channelId(m.getTextChannel().getId())
				.date(Date.from(a.getCreationTime().toInstant()))
				.name(a.getFileName())
				.image(a.isImage())
				.size(a.getSize())
				.id(a.getId())
				.build();

		try {
			@Cleanup val stream = a.getInputStream();
			fileStorageService.storeFile(metadata, stream);
		} catch (IOException e) {
			log.error("Cannot get attachment input stream", e);
			// just ignore...
		}
	}

}
