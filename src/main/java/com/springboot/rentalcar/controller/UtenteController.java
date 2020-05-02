package com.springboot.rentalcar.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.rentalcar.entity.Utente;
import com.springboot.rentalcar.exception.NotFoundException;
import com.springboot.rentalcar.jwt.JwtTokenUtil;
import com.springboot.rentalcar.service.UtenteService;

@RestController
@RequestMapping("/utente")
public class UtenteController {

	@Autowired
	UtenteService utenteService;

	@Autowired
	JwtTokenUtil jwtTokenUtil;

	private static final Logger log= LoggerFactory.getLogger(UtenteController.class);

	@GetMapping(value = "/utente",produces = "application/json" )
	public ResponseEntity<Utente> utente(Authentication auth, 
			@RequestHeader(name = "Authorization") String token
			) throws NotFoundException{

		String jwt = token.substring(7);
		String username = jwtTokenUtil.getUsernameFromToken(jwt);
		Utente utente = utenteService.findFirstByUsername(username);
		if (utente == null ) {
			String ErrMsg = String.format("Utente non trovato", username);
			throw new NotFoundException(ErrMsg);
		}else {
			return new ResponseEntity<Utente>(utente, HttpStatus.OK);
		}
	}

	@GetMapping(value = "/utente/all", produces = "application/json")
	public ResponseEntity<List<Utente>> listaUtente() throws NotFoundException{
		List<Utente> lista = utenteService.list();
		if (lista == null ) {
			String ErrMsg = "Lista Utenti vuota";
			throw new NotFoundException(ErrMsg);
		}else {
			return new ResponseEntity<List<Utente>>(lista, HttpStatus.OK);
		}
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/inserisci", method = RequestMethod.PUT)
	public ResponseEntity addUtente(@RequestBody Utente utente){
		System.out.println(utente);
		if (utenteService.existsByUsername(utente.getUsername())) {
			log.error("Username non disponibile!!");
			return ResponseEntity.badRequest().body("Username non disponibile!!");
		}else {
		utenteService.addUtente(utente);
		log.info("Utente salvato con successo!!!");
		return ResponseEntity.ok().body("Utente salvato con successo!!!");
		}
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/elimina/{id}", method = RequestMethod.DELETE)
	public ResponseEntity elimina(@PathVariable int id) {
		Utente utente = utenteService.getUtente(id);
		if (utente != null) {
			utenteService.deleteById(id);
			return ResponseEntity.ok().body("UTENTE ELIMINATO");
		}else {
			log.error("UTENTE NULLO");
			return ResponseEntity.badRequest().body("Utente nullo");
		}
	}
}

