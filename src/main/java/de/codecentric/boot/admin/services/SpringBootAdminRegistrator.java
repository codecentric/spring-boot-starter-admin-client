package de.codecentric.boot.admin.services;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import de.codecentric.boot.admin.model.Application;

public class SpringBootAdminRegistrator {

	@Autowired
	private Environment env;

	@PostConstruct
	public void check() {
		Assert.notNull(env.getProperty("server.port"), "The server port of the application is mandatory");
		Assert.notNull(env.getProperty("info.id"), "The id of the application is mandatory");
		Assert.notNull(env.getProperty("info.version"), "The version of the application is mandatory");
	}

	@Scheduled(cron = "0 0/1 * * * *")
	public void minutly() throws UnknownHostException, MalformedURLException {
		String id = env.getProperty("info.id");
		int port = env.getProperty("server.port", Integer.class);
		RestTemplate template = new RestTemplate();
		template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		ApplicationList list = template.getForObject("http://localhost:8080/api/applications", ApplicationList.class);
		for (Application app : list) {
			if (id.equals(app.getId())) {
				System.out.println("da!");
				return;
			}
		}
		Application app = new Application();
		app.setId(id);
		app.setUrl(new URL("http", InetAddress.getLocalHost().getHostName(), port, "").toString());
		template.postForObject("http://localhost:8080/api/applications", app, void.class);
		System.out.println("registriert!");
	}

	private static class ApplicationList extends ArrayList<Application> {
		private static final long serialVersionUID = 1L;
	}

}
