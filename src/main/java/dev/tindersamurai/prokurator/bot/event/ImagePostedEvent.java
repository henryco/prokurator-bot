package dev.tindersamurai.prokurator.bot.event;

import dev.tindersamurai.prokurator.bot.ProkuratorBotEventListener;
import dev.tindersamurai.prokurator.mvc.data.dao.jpa.MediaPostRepo;
import dev.tindersamurai.prokurator.mvc.data.dao.jpa.TextChannelRepo;
import dev.tindersamurai.prokurator.mvc.data.dao.jpa.UserRepo;
import dev.tindersamurai.prokurator.mvc.data.entity.post.MediaPost;
import dev.tindersamurai.prokurator.mvc.service.storage.FileStorageService;
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
			val name = processFileName(m, fileName);
			val fid = fileStorageService.storeFile(stream, name);
			mediaPost.setFileId(fid);
		} catch (Exception e) {
			log.error("Error while saving media file", e);
			return;
		}

		mediaPostRepo.save(mediaPost);
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

		return guild + s + parent + s + channel + s + date + s + author + s + file;
	}

}
