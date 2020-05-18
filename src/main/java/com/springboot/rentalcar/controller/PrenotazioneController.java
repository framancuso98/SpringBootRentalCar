package com.springboot.rentalcar.controller;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.springboot.rentalcar.entity.Auto;
import com.springboot.rentalcar.entity.Prenotazione;
import com.springboot.rentalcar.entity.Utente;
import com.springboot.rentalcar.jwt.JwtTokenUtil;
import com.springboot.rentalcar.service.AutoService;
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

	@Autowired 
	AutoService autoService;
	
	@Autowired
	Gson gson;

	private static final Logger log= LoggerFactory.getLogger(PrenotazioneController.class);

	@RequestMapping(value = "/all", produces = "application/json", method = RequestMethod.GET)
	public ResponseEntity<List<Prenotazione>> listaPrenotazioneUtente(Authentication auth, @RequestHeader("Authorization") String token) 
			throws NotFoundException{
		try {
			String jwt = token.substring(7);
			String username = jwtTokenUtil.getUsernameFromToken(jwt);

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
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<Prenotazione>>(HttpStatus.BAD_REQUEST);
		}
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/elimina/{id}", method = RequestMethod.DELETE)
	public ResponseEntity elimina(@PathVariable int id) {
		try {	
			System.out.println(id);
			Prenotazione prenotazione = prenotazioneService.getPrenotazione(id);
			if (prenotazione != null) {
				prenotazioneService.deleteById(id);
				Gson gson = new Gson();
				String msg = gson.toJson("PRENOTAZIONE ELIMINATA");
				return ResponseEntity.ok().body(msg);
			}else {
				log.error("PRENOTAZIONE NULLO");
				Gson gson = new Gson();
				String msg = gson.toJson("PRENOTAZIONE NON PRESENTE");
				return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Gson gson = new Gson();
			String msg = gson.toJson("IMPOSSIBILE ELIMINARE QUESTA PRENOTAZIUONE");
			return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
		}
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/aggiungi", method = RequestMethod.PUT)
	public ResponseEntity addPrenotazione(Authentication auth,
			@RequestHeader (name = "Authorization")String token)
					throws com.springboot.rentalcar.exception.NotFoundException {
		try {
			int id_auto = 3;
			Auto auto = autoService.findFirstById(id_auto);
			String jwt = token.substring(7);
			String username = jwtTokenUtil.getUsernameFromToken(jwt);
			Utente utente = utenteService.findFirstByUsername(username);
			List<Prenotazione> pU = prenotazioneService.findFirstByUtente(utente);
			List<Prenotazione> pA = prenotazioneService.findFirstByAuto(auto);
			if (pU.size()>=1) {
				log.error("L'UTENTE "+ username.toUpperCase() + " HA GIA UNA PRENOTAZIONE ATTIVA");
				Gson gson = new Gson();
				String msg = gson.toJson("L'UTENTE "+ username.toUpperCase() + " HA GIA UNA PRENOTAZIONE ATTIVA");
				return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
			}else if (pA.size()>=1) {
				log.error("AUTO NON DISPONIBILE");
				Gson gson = new Gson();
				String msg = gson.toJson("AUTO NON DISPONIBILE");
				return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
			}else {
				Prenotazione p = new Prenotazione();
				p.setAuto(auto);
				p.setUtente(utente);
				prenotazioneService.addPrenotazione(p);
				Gson gson = new Gson();
				String msg = gson.toJson("PRENOTAZIONE SALVATA CON SUCCESSO");
				return ResponseEntity.ok().body(msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Gson gson = new Gson();
			String msg = gson.toJson("IMPOSSIBILE AGGIUNGERE UNA PRENOTAZIONE");
			return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
		}
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/accetta", method = RequestMethod.PUT)
	public ResponseEntity accettaPrenotazione(@RequestBody Prenotazione prenotazione) {
		try {

			prenotazioneService.accettaPrenotazione(prenotazione);
			Gson gson = new Gson();
			String msg = gson.toJson("PRENOTAZIONE ACCETTATA");
			return ResponseEntity.ok().body(msg);
		} catch (Exception e) {
			e.printStackTrace();
			Gson gson = new Gson();
			String msg = gson.toJson("IMPOSSIBILE ACCETTARE QUESTA PRENOTAZIUONE");
			return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
		}
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/rifiuta", method = RequestMethod.PUT)
	public ResponseEntity rifiutaPrenotazione(@RequestBody Prenotazione prenotazione) {
		try {
			prenotazioneService.rifiutaPrenotazione(prenotazione);
			Gson gson = new Gson();
			String msg = gson.toJson("PRENOTAZIONE RIFIUTATA");
			return ResponseEntity.ok().body(msg);
		} catch (Exception e) {
			e.printStackTrace();
			Gson gson = new Gson();
			String msg = gson.toJson("IMPOSSIBILE RIFIUTARE QUESTA PRENOTAZIUONE");
			return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
		}
	}
}
