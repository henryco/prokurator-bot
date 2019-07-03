package dev.tindersamurai.atencjobot.bot.event;

import dev.tindersamurai.atencjobot.bot.ProkuratorBotEventListener;
import dev.tindersamurai.atencjobot.mvc.service.storage.FileStorageService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

		val group = message.getGroup();
		val guild = message.getGuild();

		val avatarUrl = message.getAuthor().getAvatarUrl();
		val name = message.getAuthor().getName();
		val tag = message.getAuthor().getAsTag();

		val channelName = message.getChannel().getName();
		val channelId = message.getChannel().getId();

		val date = message.getCreationTime().toString();
		val type = message.getType();


		log.info("---AUTHOR---");
		log.info("TAG: {}", tag);
		log.info("NAME: {}", name);
		log.info("AVATAR: {}", avatarUrl);

		log.info("---CHANNEL---");
		log.info("NAME: {}", channelName);
		log.info("ID: {}", channelId);

		log.info("---CONTENT---");
		log.info("TYPE: {}", type);
		log.info("DATE: {}", date);

		log.info("---GUILD---");
		log.info("NAME: {}", guild.getName());
		log.info("ID: {}", guild.getId());

		log.info("---GROUP---");
		log.info("{}", group);

		log.info("---TEXT---");
		val textName = message.getTextChannel().getName();
		val textId = message.getTextChannel().getId();
		log.info("NAME: {}", textName);
		log.info("ID: {}", textId);

		val par = message.getTextChannel().getParent();
		log.info("---PARENT---");
		log.info("P: {}", par);
		if (par != null)
			log.info("NAME: {}", par.getName());

		val attachments = message.getAttachments();
		for (val a : attachments) {
			val fileName = a.getFileName();
			val image = a.isImage();
			val size = a.getSize();
			val url = a.getUrl();
//			a.download()

			log.info("---ATTACHMENT---");
			log.info("NAME: {}", fileName);
			log.info("IMAGE: {}", image);
			log.info("SIZE: {}", size);
			log.info("URL: {}", url);
		}

		log.info("---END---");
	}
}
