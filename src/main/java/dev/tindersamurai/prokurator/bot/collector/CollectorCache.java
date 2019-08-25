package dev.tindersamurai.prokurator.bot.collector;

import dev.tindersamurai.prokurator.backend.commons.entity.CollectorEvent;
import dev.tindersamurai.prokurator.backend.commons.service.ICollectorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service @Slf4j
public class CollectorCache {

	private final ICollectorService collectorService;

	@Autowired
	public CollectorCache(ICollectorService collectorService) {
		this.collectorService = collectorService;
	}

	@Cacheable(value = "collector", key = "cacheId")
	public void collect(CollectorEvent event, String cacheId) {
		log.debug("collect: {}, {}", event, cacheId);
		collectorService.saveDiscordEvent(event);
	}
}
