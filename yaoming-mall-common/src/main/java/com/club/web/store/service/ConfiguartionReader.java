package com.club.web.store.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Service;

@Service
public class ConfiguartionReader {
	@Bean 
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {

	return new PropertySourcesPlaceholderConfigurer();

	}
}
