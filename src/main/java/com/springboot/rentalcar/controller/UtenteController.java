package com.springboot.rentalcar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.rentalcar.entity.Utente;
import com.springboot.rentalcar.exception.NotFoundException;
import com.springboot.rentalcar.service.UtenteService;

@RestController
@RequestMapping("/utente")
public class UtenteController {

	@Autowired
	UtenteService utenteService;
	
	@GetMapping(value = "/utente/{id}",produces = "application/json" )
	public ResponseEntity<Utente> utente(@PathVariable("id")int Id) throws NotFoundException{
		
		//Utente utente = utenteService.findFirstByEmail("admin@gmail.com");
		Utente utente = utenteService.getUtente(Id);
		
	if (utente == null ) {
		String ErrMsg = String.format("Utente non trovato", Id);
		throw new NotFoundException(ErrMsg);
	}else {
		return new ResponseEntity<Utente>(utente, HttpStatus.OK);
	}
		
	}
	
	@GetMapping(value = "/all", produces = "application/json")
	public ResponseEntity<List<Utente>> listaUtente() throws NotFoundException{
		List<Utente> lista = utenteService.list();
		if (lista == null ) {
			String ErrMsg = "Lista Utenti vuota";
			throw new NotFoundException(ErrMsg);
		}else {
		return new ResponseEntity<List<Utente>>(lista, HttpStatus.OK);
		}
	}
}

