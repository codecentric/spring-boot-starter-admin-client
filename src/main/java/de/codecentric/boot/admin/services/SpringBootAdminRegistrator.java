package de.codecentric.boot.admin.services;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import de.codecentric.boot.admin.model.Application;

/**
 * Scheduler that checks the registration of the application at the spring-boot-admin.
 */
public class SpringBootAdminRegistrator {

	private static final Logger LOGGER = LoggerFactory.getLogger(SpringBootAdminRegistrator.class);

	@Autowired
	private Environment env;

	// the URL of the admin application - default: localhost:8080
	@Value("${boot.admin.url:http://localhost:8080}")
	private String adminUrl;

	@PostConstruct
	public void check() {
		Assert.notNull(env.getProperty("server.port"), "The server port of the application is mandatory");
		Assert.notNull(env.getProperty("info.id"), "The id of the application is mandatory");
		Assert.notNull(env.getProperty("info.version"), "The version of the application is mandatory");
		Assert.notNull(env.getProperty("boot.admin.url"), "The URL of the spring-boot-admin application is mandatory");
	}

	/**
	 * Checks minutly if this application is registered at the admin application and registeres itself, if neccesary.
	 * 
	 * @throws UnknownHostException
	 * @throws MalformedURLException
	 */
	@Scheduled(cron = "0 0/1 * * * *")
	public void minutly() throws UnknownHostException, MalformedURLException {
		String id = env.getProperty("info.id");
		int port = env.getProperty("server.port", Integer.class);
		RestTemplate template = new RestTemplate();
		template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		ApplicationList list = template.getForObject(adminUrl + "/api/applications", ApplicationList.class);
		for (Application app : list) {
			if (id.equals(app.getId())) {
				// the application is already registered at the admin tool
				return;
			}
		}
		// register the application with the used URL and port
		String url = new URL("http", InetAddress.getLocalHost().getHostName(), port, "").toString();
		Application app = new Application();
		app.setId(id);
		app.setUrl(url);
		template.postForObject(adminUrl + "/api/applications", app, void.class);
		LOGGER.info("Application registered itself at the admin application with ID {} and URL {}", id, url);
	}

	private static class ApplicationList extends ArrayList<Application> {
		private static final long serialVersionUID = 1L;
	}

}
