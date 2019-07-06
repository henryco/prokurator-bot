package dev.tindersamurai.atencjobot.init.collector;

import dev.tindersamurai.atencjobot.mvc.data.dao.jpa.GuildRepo;
import dev.tindersamurai.atencjobot.mvc.data.dao.jpa.TextChannelRepo;
import dev.tindersamurai.atencjobot.mvc.data.dao.jpa.UserRepo;
import dev.tindersamurai.atencjobot.mvc.data.entity.Guild;
import dev.tindersamurai.atencjobot.mvc.data.entity.TextChannel;
import dev.tindersamurai.atencjobot.mvc.data.entity.User;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component @Slf4j
public class BotEventCollector extends ListenerAdapter implements IBotEventCollector {

	private final TextChannelRepo textChannelRepo;
	private final GuildRepo guildRepo;
	private final UserRepo userRepo;

	@Autowired
	public BotEventCollector(
			TextChannelRepo textChannelRepo,
			GuildRepo guildRepo,
			UserRepo userRepo
	) {
		this.textChannelRepo = textChannelRepo;
		this.guildRepo = guildRepo;
		this.userRepo = userRepo;
	}

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		log.debug("Collector event");
		collectData(event.getMessage());
	}

	@Transactional
	protected void collectData(Message message) {
		log.debug("collectData({})", message);
		val user = new User(); {
			val author = message.getAuthor();
			user.setAvatar(author.getAvatarUrl());
			user.setName(author.getName());
			user.setId(author.getId());
			userRepo.save(user);
		}

		val guild = new Guild(); {
			val _guild = message.getGuild();
			guild.setIconUrl(_guild.getIconUrl());
			guild.setName(_guild.getName());
			guild.setId(_guild.getIconId());
			guildRepo.save(guild);
		}

		val textChannel = new TextChannel(); {
			val channel = message.getTextChannel();
			textChannel.setName(channel.getName());
			textChannel.setNsfw(channel.isNSFW());
			textChannel.setId(channel.getId());
			textChannel.setGuild(guild);

			val parent = channel.getParent();
			if (parent != null)
				textChannel.setCategory(parent.getName());
			textChannelRepo.save(textChannel);
		}
	}

}
