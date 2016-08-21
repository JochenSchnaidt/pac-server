package com.prodyna.pac.rest;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

class Hero {

	public Hero(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int id;
	public String name;
}

@RestController
@RequestMapping("/heroes")
public class HeroesController extends AbstractController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@CrossOrigin(origins = "http://localhost:3000")
	@RequestMapping(path = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<Hero>> getUser() {

		log.info("get heroes for ["  + "]");

		List<Hero> response = new ArrayList<Hero>();

		response.add(new Hero(1, "Mr. Nice"));
		response.add(new Hero(2, "Narco"));
		response.add(new Hero(3, "Bombasto"));
		response.add(new Hero(4, "Celeritas"));
		response.add(new Hero(5, "Magneta"));
		response.add(new Hero(6, "RubberMan"));
		response.add(new Hero(7, "Dynama"));
		response.add(new Hero(8, "Dr IQ"));
		response.add(new Hero(9, "Magma"));
		response.add(new Hero(10, "Tornado"));

		ResponseEntity<List<Hero>> responseEntity = new ResponseEntity<List<Hero>>(response, HttpStatus.OK);
		return responseEntity;
	}

}