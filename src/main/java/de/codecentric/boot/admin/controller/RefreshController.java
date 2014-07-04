package de.codecentric.boot.admin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Refresh the application context i.e. to reload changed properties files.
 */
@Controller
public class RefreshController {

	private static final Logger LOGGER = LoggerFactory.getLogger(RefreshController.class);

	@Autowired
	private ConfigurableApplicationContext context;

	@RequestMapping(value = "/refresh", method = RequestMethod.POST)
	@ResponseBody
	public String refresh() {
		// Doesn't work in spring-boot at the moment ... (v 1.1.0)
		LOGGER.warn("Refreshing application doesn't work at the moment");
		return "Refreshing application doesn't work at the moment";
		// context.refresh();
	}

}
