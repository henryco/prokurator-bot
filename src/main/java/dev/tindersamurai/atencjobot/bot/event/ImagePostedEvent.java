package dev.tindersamurai.atencjobot.bot.event;

import dev.tindersamurai.atencjobot.bot.ProkuratorBotEventListener;
import dev.tindersamurai.atencjobot.mvc.data.dao.jpa.MediaPostRepo;
import dev.tindersamurai.atencjobot.mvc.data.dao.jpa.TextChannelRepo;
import dev.tindersamurai.atencjobot.mvc.data.dao.jpa.UserRepo;
import dev.tindersamurai.atencjobot.mvc.data.entity.post.MediaPost;
import dev.tindersamurai.atencjobot.mvc.service.storage.FileStorageService;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.Message.Attachment;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;

@Component @Slf4j
public class ImagePostedEvent extends ProkuratorBotEventListener {

	private final FileStorageService fileStorageService;
	private final TextChannelRepo textChannelRepo;
	private final MediaPostRepo mediaPostRepo;
	private final UserRepo userRepo;

	@Autowired
	public ImagePostedEvent(
			FileStorageService fileStorageService,
			TextChannelRepo textChannelRepo,
			MediaPostRepo mediaPostRepo,
			UserRepo userRepo
	) {
		this.fileStorageService = fileStorageService;
		this.textChannelRepo = textChannelRepo;
		this.mediaPostRepo = mediaPostRepo;
		this.userRepo = userRepo;
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

	@Transactional
	protected void processAttachment(Message m, Attachment a) {
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

		val channel = textChannelRepo.getOne(m.getTextChannel().getId());
		val author = userRepo.getOne(m.getAuthor().getId());

		val mediaPost = new MediaPost(); {
			mediaPost.setDate(Date.from(a.getCreationTime().toInstant()));
			mediaPost.setChannel(channel);
			mediaPost.setAuthor(author);
			mediaPost.setName(fileName);
			mediaPost.setImage(image);
			mediaPost.setSize(size);
			mediaPost.setId(id);
		}

		try {
			@Cleanup val stream = a.getInputStream();
			val fid = fileStorageService.storeFile(stream);
			mediaPost.setFileId(fid);
		} catch (Exception e) {
			log.error("Error while saving media file", e);
			return;
		}

		mediaPostRepo.save(mediaPost);
	}

}
