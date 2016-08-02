package com.prodyna.pac.rest;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.prodyna.pac.service.SystemService;

import static com.prodyna.pac.Constants.SYSTEM_VARIABLE;

@Controller
public class SystemController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SystemService service;

    @Value("${application.name}")
    String appName;

    @Value("${application.version}")
    String appVersion;

    @RequestMapping(path = "/system/", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    String home() {

        System.getenv().entrySet().forEach(k -> {
            if (SYSTEM_VARIABLE.equals(k.getKey())) {
                log.error(k.getKey() + ": " + k.getValue());
            }
        });

        StringBuilder sb = new StringBuilder();
        sb.append("Request on ").append(LocalDateTime.now().toString()).append(" returned:\n");
        sb.append("Application: ").append(appName).append("\n");
        sb.append("Version: ").append(appVersion).append("\n");
        sb.append("Database is available: ").append(service.checkSystem().isDatabaseIsAvailable()).append("\n");
        sb.append("System is up and running");

        return sb.toString();
    }

}