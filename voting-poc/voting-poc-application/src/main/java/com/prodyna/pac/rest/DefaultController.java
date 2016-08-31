package com.prodyna.pac.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Default controller to handle all requests incoming on the root URL.
 */
@Controller
public class DefaultController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(path = "/", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String home() {
		log.debug("default debug");
		return "Default works fine!";
	}

}