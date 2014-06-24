package de.codecentric.boot.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Refresh the application context i.e. to reload changed properties files.
 */
@Controller
public class RefreshController {

	@Autowired
	private ConfigurableApplicationContext context;

	@RequestMapping(value = "/admin/refresh", method = RequestMethod.POST)
	public void refresh() {
		context.refresh();
	}

}
