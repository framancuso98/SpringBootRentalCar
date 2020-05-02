package com.springboot.rentalcar.controller;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.rentalcar.entity.Prenotazione;
import com.springboot.rentalcar.entity.Utente;
import com.springboot.rentalcar.jwt.JwtTokenUtil;
import com.springboot.rentalcar.service.PrenotazioneService;
import com.springboot.rentalcar.service.UtenteService;

import javassist.NotFoundException;

@RestController
@RequestMapping("/prenotazione")
public class PrenotazioneController {
	@Autowired
	PrenotazioneService prenotazioneService;
	@Autowired
	UtenteService utenteService;
	
	@Autowired
	JwtTokenUtil jwtTokenUtil;
	
	private static final Logger log= LoggerFactory.getLogger(PrenotazioneController.class);
	
	@RequestMapping(value = "/all", produces = "application/json", method = RequestMethod.GET)
	public ResponseEntity<List<Prenotazione>> listaPrenotazioneUtente(Authentication auth, @RequestHeader("Authorization") String token) 
			throws NotFoundException{
		String jwt = token.substring(7);
		String username = jwtTokenUtil.getUsernameFromToken(jwt);
		
		//String username = auth.getName();
		Utente utente = utenteService.findFirstByUsername(username);
		String ruolo = utente.getRuolo().getRuolo();
		if (ruolo != null && ruolo.equalsIgnoreCase("ROLE_USER")) {
			List<Prenotazione> prenotazione = prenotazioneService.findFirstByUtente(utente);
			if (prenotazione == null ) {
				String ErrMsg = "Lista Prenotazioni vuota";
				throw new NotFoundException(ErrMsg);
			}else {
			return new ResponseEntity<List<Prenotazione>>( prenotazione, HttpStatus.OK);
			}
		}else if(ruolo != null && ruolo.equalsIgnoreCase("ROLE_ADMIN")){
			List<Prenotazione> lista = prenotazioneService.list();
			if (lista == null ) {
				String ErrMsg = "Lista Prenotazioni vuota";
				throw new NotFoundException(ErrMsg);
			}else {
			return new ResponseEntity<List<Prenotazione>>(lista, HttpStatus.OK);
			}
		}
		String ErrMsg = "Lista Prenotazioni vuota";
		throw new NotFoundException(ErrMsg);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/elimina/{id}", method = RequestMethod.DELETE)
	public ResponseEntity elimina(@PathVariable int id) {
		System.out.println(id);
		Prenotazione prenotazione = prenotazioneService.getPrenotazione(id);
		if (prenotazione != null) {
			prenotazioneService.deleteById(id);
			return ResponseEntity.ok().body("PRENOTAZIONE ELIMINATA");
		}else {
			log.error("PRENOTAZIONE NULLO");
			return ResponseEntity.badRequest().body("PRENOTAZIONE NON PRESENTE");
		}
	}
}
