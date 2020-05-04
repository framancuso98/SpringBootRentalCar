package com.springboot.rentalcar.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
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

	@Autowired
	PasswordEncoder passwordEncoder;

	private static final Logger log= LoggerFactory.getLogger(UtenteController.class);

	@RequestMapping(value = "/profilo",produces = "application/json", method = RequestMethod.GET)
	public ResponseEntity<Utente> utente(Authentication auth, 
			@RequestHeader(name = "Authorization") String token
			) throws NotFoundException{
		try {
			String jwt = token.substring(7);
			String username = jwtTokenUtil.getUsernameFromToken(jwt);
			Utente utente = utenteService.findFirstByUsername(username);
			if (utente == null ) {
				String ErrMsg = String.format("Utente non trovato", username);
				throw new NotFoundException(ErrMsg);
			}else {
				return new ResponseEntity<Utente>(utente, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Utente>(HttpStatus.BAD_REQUEST);
		}
	}


	@GetMapping(value = "/utente/all", produces = "application/json")
	public ResponseEntity<List<Utente>> listaUtente() throws NotFoundException{
		try {
			List<Utente> lista = utenteService.list();
			if (lista == null ) {
				String ErrMsg = "Lista Utenti vuota";
				throw new NotFoundException(ErrMsg);
			}else {
				return new ResponseEntity<List<Utente>>(lista, HttpStatus.OK);
			}	
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<Utente>>(HttpStatus.BAD_REQUEST);
		}

	}


	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/inserisci", method = RequestMethod.PUT)
	public ResponseEntity addUtente(@RequestBody Utente utente){
		try {
			System.out.println(utente);
			if (utenteService.existsByUsername(utente.getUsername())) {
				log.error("Username non disponibile!!");
				return ResponseEntity.badRequest().body("Username non disponibile!!");
			}else {
				String pass = passwordEncoder.encode(utente.getPassword());
				utente.setPassword(pass);
				utenteService.addUtente(utente);
				log.info("Utente salvato con successo!!!");
				return ResponseEntity.ok().body("Utente salvato con successo!!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body("IMPOSSIBILE INSERIRE L'UTENTE");
		}

	}


	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/elimina/{id}", method = RequestMethod.DELETE)
	public ResponseEntity eliminaUtente(@PathVariable int id) {
		try {
			Utente utente = utenteService.getUtente(id);
			if (utente != null) {
				utenteService.deleteById(id);
				return ResponseEntity.ok().body("UTENTE ELIMINATO");
			}else {
				log.error("UTENTE NULLO");
				return ResponseEntity.badRequest().body("Utente nullo");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body("IMPOSSIBILE ELIMINARE L'UTENTE");
		}
	}


	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/modifica", method = RequestMethod.PUT)
	public ResponseEntity modificaUtente(@RequestBody Utente newUtente) {
		try {
			int id_utente = newUtente.getId();
			Utente utente = utenteService.getUtente(id_utente);
			if (utenteService.existsByUsername(newUtente.getUsername()) && !newUtente.getUsername().equalsIgnoreCase(utente.getUsername())){
				log.error("Username non disponibile!!");
				return ResponseEntity.badRequest().body("Username non disponibile!!");
			}else {
				utente.setUsername(newUtente.getUsername());
				utente.setPassword(passwordEncoder.encode(newUtente.getPassword()));
				utente.setNome(newUtente.getNome());
				utente.setCognome(newUtente.getCognome());
				utente.setData_nascita(newUtente.getData_nascita());
				utente.setRuolo(newUtente.getRuolo());
				utenteService.addUtente(utente);
				return ResponseEntity.ok().body("UTENTE MODIFICATO CON SUCCESSO");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body("IMPOSSIBILE MODIFICARE L'UTENTE "+ newUtente.getUsername());
		}
	}
}

