package com.springboot.rentalcar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.rentalcar.model.Utente;
import com.springboot.rentalcar.service.UtenteService;

@RestController
@RequestMapping("/utente")
public class UtenteController {

	@Autowired
	UtenteService utenteService;
	
	@GetMapping(value = "/utente",produces = "application/json" )
	public ResponseEntity<Utente> utente(){
		int id = 1;
		//Utente utente = utenteService.findFirstByEmail("admin@gmail.com");
		Utente utente = utenteService.getUtente(id);
		return new ResponseEntity<Utente>(utente, HttpStatus.OK);
		
	}
	
	@GetMapping(value = "/all", produces = "application/json")
	public ResponseEntity<List<Utente>> listaUtente(){
		List<Utente> lista = utenteService.list();
		return new ResponseEntity<List<Utente>>(lista, HttpStatus.OK);
	}
}

