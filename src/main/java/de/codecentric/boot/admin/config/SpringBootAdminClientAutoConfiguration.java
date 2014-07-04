package de.codecentric.boot.admin.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import de.codecentric.boot.admin.controller.LogfileController;
import de.codecentric.boot.admin.controller.RefreshController;
import de.codecentric.boot.admin.services.SpringBootAdminRegistratorTask;
import de.codecentric.boot.admin.web.SimpleCORSFilter;

/**
 * This configuration adds a registrator bean to the spring context. This bean checks periodicaly, if the using
 * application is registered at the spring-boot-admin application. If not, it registers itself.
 */
@Configuration
public class SpringBootAdminClientAutoConfiguration {

	/**
	 * Task that registers the application at the spring-boot-admin application.
	 */
	@Bean
	public SpringBootAdminRegistratorTask registrator() {
		return new SpringBootAdminRegistratorTask();
	}

	/**
	 * HTTP filter to enable Cross-Origin Resource Sharing.
	 */
	@Bean
	public SimpleCORSFilter corsFilter() {
		return new SimpleCORSFilter();
	}

	/**
	 * TaskRegistrar that triggers the RegistratorTask every one second.
	 */
	@Bean
	public ScheduledTaskRegistrar taskRegistrar() {
		ScheduledTaskRegistrar registrar = new ScheduledTaskRegistrar();
		registrar.addFixedRateTask(registrator(), 1000);
		return registrar;
	}

	/**
	 * Controller to do something with the application logfile(s).
	 */
	@Bean
	@ConditionalOnProperty("logging.file")
	public LogfileController logfileController() {
		return new LogfileController();
	}

	/**
	 * Controller to do a refresh on the application.
	 */
	@Bean
	public RefreshController refreshController() {
		return new RefreshController();
	}

}
