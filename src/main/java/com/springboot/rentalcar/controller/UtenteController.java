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

import com.google.gson.Gson;
import com.springboot.rentalcar.entity.Ruolo;
import com.springboot.rentalcar.entity.Utente;
import com.springboot.rentalcar.exception.NotFoundException;
import com.springboot.rentalcar.jwt.JwtTokenUtil;
import com.springboot.rentalcar.service.RuoloService;
import com.springboot.rentalcar.service.UtenteService;

@RestController
@RequestMapping("/utente")
public class UtenteController {

	@Autowired
	UtenteService utenteService;
	
	@Autowired
	RuoloService ruoloService;

	@Autowired
	JwtTokenUtil jwtTokenUtil;

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	Gson gson;

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


	@GetMapping(value = "/all", produces = "application/json")
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


	
	@RequestMapping(value = "/inserisci", method = RequestMethod.POST)
	public ResponseEntity<String> addUtente(@RequestBody Utente utente){
		try {
			System.out.println(utente);
			if (utenteService.existsByUsername(utente.getUsername())) {
				log.error("Username non disponibile!!");
				Gson gson = new Gson();
				String msg = gson.toJson("Username non disponibile!!");
				return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
			}else {
				String pass = passwordEncoder.encode(utente.getPassword());
				utente.setPassword(pass);
				utenteService.addUtente(utente);
				Gson gson = new Gson();
				String msg = gson.toJson("Utente salvato con successo!!!");
				log.info("Utente salvato con successo!!!");
				return ResponseEntity.ok().body(msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Gson gson = new Gson();
			String msg = gson.toJson("IMPOSSIBILE INSERIRE L'UTENTE");
			return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
		}
	}


	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/elimina/{id}", method = RequestMethod.DELETE)
	public ResponseEntity eliminaUtente(@PathVariable int id) {
		try {
			Utente utente = utenteService.getUtente(id);
			if (utente != null) {
				utenteService.deleteById(id);
				log.info("Utente eliminato con successo!!!");
				Gson gson = new Gson();
				String msg = gson.toJson("Utente eliminato!!");
				return ResponseEntity.ok().body(msg);
			}else {
				log.error("UTENTE NULLO");
				Gson gson = new Gson();
				String msg = gson.toJson("UTENTE NULLO!!");
				return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Gson gson = new Gson();
			String msg = gson.toJson("IMPOSSIBILE ELIMINARE L'UTENTE");
			return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
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
				Gson gson = new Gson();
				String msg = gson.toJson("Username non disponibile!!");
				return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
			}else {
				utente.setUsername(newUtente.getUsername());
				utente.setPassword(passwordEncoder.encode(newUtente.getPassword()));
				utente.setNome(newUtente.getNome());
				utente.setCognome(newUtente.getCognome());
				utente.setData_nascita(newUtente.getData_nascita());
				utente.setRuolo(newUtente.getRuolo());
				utenteService.addUtente(utente);
				log.error("Utente salvato con successo!!!");
				Gson gson = new Gson();
				String msg = gson.toJson("Utente salvato con successo!!!");
				return ResponseEntity.ok().body(msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Gson gson = new Gson();
			String msg = gson.toJson("IMPOSSIBILE MODIFICARE L'UTENTE "+ newUtente.getUsername());
			return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/registra", method = RequestMethod.POST)
	public ResponseEntity registraUtente(@RequestBody Utente utente){
		try {
			System.out.println(utente);
			if (utenteService.existsByUsername(utente.getUsername())) {
				log.error("Username non disponibile!!");
				Gson gson = new Gson();
				String msg = gson.toJson("Username non disponibile!!");
				return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
			}else {
				String pass = passwordEncoder.encode(utente.getPassword());
				utente.setPassword(pass);
				Ruolo ruolo = ruoloService.findFirstById(2);
				utente.setRuolo(ruolo);
				utenteService.addUtente(utente);
				log.info("Utente salvato con successo!!!");
				Gson gson = new Gson();
				String msg = gson.toJson("Registrazione avvenuta con successo!!!");
				return ResponseEntity.ok().body(msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Gson gson = new Gson();
			String msg = gson.toJson("IMPOSSIBILE INSERIRE L'UTENTE");
			return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
		}
	}
}

