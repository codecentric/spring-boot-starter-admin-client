package de.codecentric.boot.admin.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller that provides an API for logfiles, i.e. downloading the main logfile configured in environment property
 * 'logging.file' that is standard, but optional property for spring-boot applications.
 */
@Controller
public class LogfileController {

	private static final Logger LOGGER = LoggerFactory.getLogger(LogfileController.class);

	@Autowired
	private Environment env;

	@RequestMapping("/logfile")
	public void getLogfile(HttpServletResponse response, Errors errors) {
		String path = env.getProperty("logging.file");
		if (path == null) {
			LOGGER.error("Logfile download failed for missing property 'logging.file'");
			return;
		}
		Resource file = new FileSystemResource(path);
		if (!file.exists()) {
			LOGGER.error("Logfile download failed for missing file at path=" + path);
		} else {
			response.setContentType("application/octet-stream");
			try {
				FileCopyUtils.copy(file.getInputStream(), response.getOutputStream());
			} catch (IOException e) {
				LOGGER.error("Logfile download failed for path=" + path);
			}
		}
	}

}
