package dev.tindersamurai.prokurator.bot.collector;

import dev.tindersamurai.prokurator.backend.commons.entity.CollectorEvent;
import dev.tindersamurai.prokurator.backend.commons.entity.CollectorEvent.ChannelEntity;
import dev.tindersamurai.prokurator.backend.commons.entity.CollectorEvent.DiscordEntity;
import dev.tindersamurai.prokurator.backend.commons.service.ICollectorService;
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

	private final ICollectorService collectorService;

	@Autowired
	public BotEventCollector(ICollectorService collectorService) {
		this.collectorService = collectorService;
	}

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		log.debug("Collector event");
		collectData(event.getMessage());
	}

	@Transactional
	protected void collectData(Message message) {
		log.debug("collectData({})", message);

		val a = message.getAuthor();
		val user = new DiscordEntity(a.getId(), a.getName(), a.getAvatarUrl());

		log.debug("user: {}", user);

		val g = message.getGuild();
		val guild = new DiscordEntity(g.getId(), g.getName(), g.getId());

		log.debug("guild: {}", guild);

		val t = message.getTextChannel();
		val channelBuilder = ChannelEntity.builder(); {
			channelBuilder.name(t.getName());
			channelBuilder.nsfw(t.isNSFW());
			channelBuilder.id(t.getId());
			channelBuilder.guild(guild);
			val parent = t.getParent();
			if (t.getParent() != null)
				channelBuilder.category(parent.getName());
		}

		val entity = new CollectorEvent(user, channelBuilder.build());
		log.debug("entity: {}", entity);

		collectorService.saveDiscordEvent(entity);
	}

}
