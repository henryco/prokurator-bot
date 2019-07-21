package dev.tindersamurai.prokurator.mvc.service.collector.db;

import dev.tindersamurai.prokurator.mvc.data.dao.jpa.GuildRepo;
import dev.tindersamurai.prokurator.mvc.data.dao.jpa.TextChannelRepo;
import dev.tindersamurai.prokurator.mvc.data.dao.jpa.UserRepo;
import dev.tindersamurai.prokurator.mvc.data.entity.Guild;
import dev.tindersamurai.prokurator.mvc.data.entity.TextChannel;
import dev.tindersamurai.prokurator.mvc.data.entity.User;
import dev.tindersamurai.prokurator.mvc.service.collector.CollectorService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @Slf4j
public class LocalCollectorService implements CollectorService {

	private final TextChannelRepo textChannelRepo;
	private final GuildRepo guildRepo;
	private final UserRepo userRepo;

	@Autowired
	public LocalCollectorService(
			TextChannelRepo textChannelRepo,
			GuildRepo guildRepo,
			UserRepo userRepo
	) {
		this.textChannelRepo = textChannelRepo;
		this.guildRepo = guildRepo;
		this.userRepo = userRepo;
	}

	@Override @Transactional
	public void saveDiscordEvent(EventEntity entity) {
		log.debug("saveDiscordEvent: {}", entity);

		val user = new User(); {
			val author = entity.getUser();
			user.setAvatar(author.getAvatar());
			user.setName(author.getName());
			user.setId(author.getId());
			userRepo.save(user);
		}

		val guild = new Guild(); {
			val _guild = entity.getChannel().getGuild();
			guild.setIconUrl(_guild.getAvatar());
			guild.setName(_guild.getName());
			guild.setId(_guild.getId());
			guildRepo.save(guild);
		}

		val textChannel = new TextChannel(); {
			val channel = entity.getChannel();
			textChannel.setCategory(channel.getCategory());
			textChannel.setName(channel.getName());
			textChannel.setNsfw(channel.getNsfw());
			textChannel.setId(channel.getId());
			textChannel.setGuild(guild);
			textChannelRepo.save(textChannel);
		}
	}
}
