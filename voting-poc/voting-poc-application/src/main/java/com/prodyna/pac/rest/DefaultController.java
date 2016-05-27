package com.prodyna.pac.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.prodyna.pac.config.ApplicationConfig;

@Controller
public class DefaultController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ApplicationConfig environment;

    @Value("${spring.application.name}")
    String name;

    @RequestMapping(path = "/", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    String home() {

        log.debug("default debug");

        System.out.println("env: " + environment);

        System.out.println("env name: " + environment.getName());

        System.out.println("env ver: " + environment.getVersion());
        System.out.println("- - -");

        System.out.println("spring.application.name: " + name);

        return "Default works fine!";
    }

}