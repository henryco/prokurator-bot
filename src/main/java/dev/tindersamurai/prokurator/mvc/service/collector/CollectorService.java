package dev.tindersamurai.prokurator.mvc.service.collector;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface CollectorService {

	@Value @Builder @AllArgsConstructor class DiscordEntity {
		private @Nonnull String id;
		private @Nonnull String name;
		private @Nullable String avatar;
	}

	@Value @Builder @AllArgsConstructor class ChannelEntity {
		private @Nonnull String id;
		private @Nonnull String name;
		private @Nullable String category;
		private @Nonnull Boolean nsfw;
		private @Nonnull DiscordEntity guild;
	}

	@Value @Builder @AllArgsConstructor class EventEntity {
		private @Nonnull DiscordEntity user;
		private @Nonnull ChannelEntity channel;
	}

	void saveDiscordEvent(EventEntity entity);
}
