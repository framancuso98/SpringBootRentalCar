package com.springboot.rentalcar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.rentalcar.entity.Prenotazione;
import com.springboot.rentalcar.entity.Utente;
import com.springboot.rentalcar.service.PrenotazioneService;
import com.springboot.rentalcar.service.UtenteService;

import javassist.NotFoundException;

@RestController
//@RequestMapping("/prenotazione")
public class PrenotazioneController {
	@Autowired
	PrenotazioneService prenotazioneService;
	@Autowired
	UtenteService utenteService;
	
	@RequestMapping(value = "/utente/all/{id}", produces = "application/json", method = RequestMethod.GET)
	public ResponseEntity<Prenotazione> listaPrenotazioneUtente(@PathVariable("id")int Id) 
			throws NotFoundException{
		Utente utente = utenteService.getUtente(Id);
		Prenotazione prenotazione = prenotazioneService.findFirstByUtente(utente);
		if (prenotazione == null ) {
			String ErrMsg = "Lista Prenotazioni vuota";
			throw new NotFoundException(ErrMsg);
		}else {
		return new ResponseEntity<Prenotazione>(prenotazione, HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/admin/all", produces = "application/json", method = RequestMethod.GET)
	public ResponseEntity<List<Prenotazione>> listaPrenotazioni() 
			throws NotFoundException{
		List<Prenotazione> lista = prenotazioneService.list();
		if (lista == null ) {
			String ErrMsg = "Lista Prenotazioni vuota";
			throw new NotFoundException(ErrMsg);
		}else {
		return new ResponseEntity<List<Prenotazione>>(lista, HttpStatus.OK);
		}
	}
}
