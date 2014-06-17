package de.codecentric.boot.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import de.codecentric.boot.admin.services.SpringBootAdminRegistrator;
import de.codecentric.boot.admin.web.SimpleCORSFilter;

@Configuration
@EnableScheduling
public class SpringBootAdminClientAutoConfiguration {

	@Bean
	public SpringBootAdminRegistrator registrator() {
		return new SpringBootAdminRegistrator();
	}

	@Bean
	public SimpleCORSFilter corsFilter() {
		return new SimpleCORSFilter();
	}

}
