package de.codecentric.boot.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import de.codecentric.boot.admin.services.SpringBootAdminRegistrator;

@Configuration
@EnableScheduling
public class SpringBootAdminClientAutoConfiguration {

	@Bean
	public SpringBootAdminRegistrator registrator() {
		return new SpringBootAdminRegistrator();
	}

}
