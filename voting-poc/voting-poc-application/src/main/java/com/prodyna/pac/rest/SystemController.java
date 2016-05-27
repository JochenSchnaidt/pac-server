package com.prodyna.pac.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.prodyna.pac.service.SystemService;

@Controller
public class SystemController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SystemService service;

    @RequestMapping(path = "/system/", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    String home() {

        log.info("system info");

        service.checkSystem();
        
        return "System performs well!";
    }

}