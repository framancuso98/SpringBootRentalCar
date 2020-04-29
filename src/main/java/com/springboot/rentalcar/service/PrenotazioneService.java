package com.springboot.rentalcar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.rentalcar.entity.Prenotazione;
import com.springboot.rentalcar.entity.Utente;
import com.springboot.rentalcar.repository.PrenotazioneRepository;
@Service
public class PrenotazioneService {
	@Autowired
	PrenotazioneRepository prenotazioneRepo;
	
	public List<Prenotazione> list(){
		return prenotazioneRepo.findAll();
	}
	
	public Prenotazione findFirstByUtente(Utente utente) {
		return prenotazioneRepo.findFirstByUtente(utente);
	}
}
