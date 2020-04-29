package com.springboot.rentalcar.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.rentalcar.entity.Utente;

@RestController
@RequestMapping("/login")
public class LoginController {

	
	@PostMapping(produces = "application/json")
	public ResponseEntity<Utente> login(String username, String Password){
		return null;	
	}
}
