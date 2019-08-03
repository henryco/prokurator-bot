package dev.tindersamurai.prokurator.prop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class CloudBasedProperties implements Properties {

	private final Environment environment;

	@Autowired
	public CloudBasedProperties(Environment environment) {
		this.environment = environment;
	}

	@Override
	public String getBackendServiceUrl() {
		return environment.getRequiredProperty("prokurator.services.backend");
	}
}
