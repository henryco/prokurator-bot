package dev.tindersamurai.prokurator.conf;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.tindersamurai.prokurator.backend.commons.client.ClientFactory;
import dev.tindersamurai.prokurator.backend.commons.service.ICollectorService;
import dev.tindersamurai.prokurator.backend.commons.service.IConfigurationService;
import dev.tindersamurai.prokurator.backend.commons.service.IFileStorageService;
import dev.tindersamurai.prokurator.backend.commons.service.IMediaService;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration @Slf4j
public class RestServicesConfiguration {

	@Value class RSCProps {
		private ObjectMapper mapper;
		private String url;
	}

	@Bean
	public RSCProps provideBackendServiceUrl(ObjectMapper mapper, Environment env) {
		log.debug("provideBackendServiceUrl: {}, {}", mapper, env);
		val host = env.getRequiredProperty("prokurator.services.backend");
		return new RSCProps(mapper, host);
	}

	@Bean
	public IFileStorageService provideStorageServiceClient(RSCProps p) {
		log.debug("provideStorageServiceClient: {}", p);
		return ClientFactory.createFileStorageClient(p.getUrl(), p.getMapper());
	}

	@Bean
	public IConfigurationService provideConfigurationServiceClient(RSCProps p) {
		log.debug("provideConfigurationServiceClient: {}", p);
		return ClientFactory.createConfigurationClient(p.getUrl(), p.getMapper());
	}

	@Bean
	public ICollectorService provideCollectorServiceClient(RSCProps p) {
		log.debug("provideCollectorServiceClient: {}", p);
		return ClientFactory.createCollectorClient(p.getUrl(), p.getMapper());
	}

	@Bean
	public IMediaService provideMediaServiceClient(RSCProps p) {
		log.debug("provideMediaServiceClient: {}", p);
		return ClientFactory.createMediaClient(p.getUrl(), p.getMapper());
	}
}
