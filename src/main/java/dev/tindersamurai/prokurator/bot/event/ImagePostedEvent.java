package dev.tindersamurai.prokurator.bot.event;

import dev.tindersamurai.prokurator.backend.commons.entity.MediaEvent;
import dev.tindersamurai.prokurator.backend.commons.service.IFileStorageService;
import dev.tindersamurai.prokurator.backend.commons.service.IMediaService;
import dev.tindersamurai.prokurator.bot.ProkuratorBotEventListener;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.Message.Attachment;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Component @Slf4j
public class ImagePostedEvent extends ProkuratorBotEventListener {

	private final IFileStorageService fileStorageService;
	private final IMediaService mediaService;

	@Autowired
	public ImagePostedEvent(
			IFileStorageService fileStorageService,
			IMediaService mediaService
	) {
		this.fileStorageService = fileStorageService;
		this.mediaService = mediaService;
	}

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		log.debug("image post event: {}", event);
		val message = event.getMessage();
		val attachments = message.getAttachments();
		if (attachments == null || attachments.isEmpty())
			return;

		attachments.forEach(a -> processAttachment(message, a));
	}

	@Transactional
	protected void processAttachment(Message m, Attachment a) {
		log.debug("processAttachment({}, {})", m, a);

		if (m == null || a == null) return;

		val fileName = a.getFileName();
		val image = a.isImage();
		val size = a.getSize();
		val url = a.getUrl();
		val id = a.getId();

		log.info("---ATTACHMENT---");
		log.info("ID: {}", id);
		log.info("NAME: {}", fileName);
		log.info("IMAGE: {}", image);
		log.info("SIZE: {}", size);
		log.info("URL: {}", url);

		val attachment = MediaEvent.Attachment.builder(); {
			attachment.created(Date.from(a.getCreationTime().toInstant()).getTime());
			attachment.name(fileName);
			attachment.image(image);
			attachment.size(size);
		}

		try {
			@Cleanup val stream = a.getInputStream();
			val name = processFileName(m, fileName);
			val fid = fileStorageService.storeFile(stream, name);

			attachment.id(fid);
		} catch (Exception e) {
			log.error("Error while saving media file", e);
		}

		val builder = MediaEvent.builder(); {
			builder.channelId(m.getTextChannel().getId());
			builder.authorId(m.getAuthor().getId());
			builder.attachment(attachment.build());
			builder.id(m.getId());
		}

		val event = builder.build();
		log.debug("EVENT: {}", event);

		mediaService.storeMedia(event);
	}


	private String processFileName(Message message, String file) {
		log.debug("processFileName({}, {})", message, file);
		val guild = message.getGuild().getName();
		val author = message.getAuthor().getName();

		val textChannel = message.getTextChannel();
		val channel = textChannel.getName();
		val parent = textChannel.getParent() != null
				? textChannel.getParent().getName()
				: "default";

		val pattern = DateTimeFormatter.ofPattern("yyyy_MM_dd");
		val date = message.getCreationTime().format(pattern);

		val s = File.separator;

		return guild + s + author + s + parent + s + date + s + channel + s + file;
	}

}
