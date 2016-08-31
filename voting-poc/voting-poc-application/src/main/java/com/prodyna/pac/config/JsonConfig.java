package com.prodyna.pac.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Configuration to specify {@code Jackson} behavior on JSON mapping.
 */
@Configuration
public class JsonConfig {

	@Bean
	public ObjectMapper jsonMapper() {
		return Jackson2ObjectMapperBuilder.json().serializationInclusion(Include.NON_NULL).serializationInclusion(Include.NON_EMPTY).featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
		        .build();
	}
}
