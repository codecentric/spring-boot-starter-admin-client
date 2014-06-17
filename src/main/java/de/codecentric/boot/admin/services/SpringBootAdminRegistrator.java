package de.codecentric.boot.admin.services;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import de.codecentric.boot.admin.model.Application;

public class SpringBootAdminRegistrator {

	@Value("${info.id}")
	private String id;

	@Value("${server.port}")
	private Integer port;

	@Scheduled(cron = "0 0/1 * * * *")
	public void minutly() throws UnknownHostException, MalformedURLException {
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
	}

}
