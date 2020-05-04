package com.springboot.rentalcar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.rentalcar.entity.Auto;
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
	
	public List<Prenotazione> findFirstByUtente(Utente utente) {
		return prenotazioneRepo.findFirstByUtente(utente);
	}
	
	public List<Prenotazione> findFirstByAuto(Auto auto){
		return prenotazioneRepo.findFirstByAuto(auto);
	}
	
	public Prenotazione getPrenotazione(int id) {
		return prenotazioneRepo.findFirstById(id);
	}
	
	public void deleteById(Integer id) {
		prenotazioneRepo.deleteById(id);
	}
	
	public void addPrenotazione(Prenotazione prenotazione) {
		prenotazione.setStato("IN SOSPESO");
		prenotazioneRepo.save(prenotazione);
	}
	
	public void accettaPrenotazione(Prenotazione prenotazione) {
		prenotazione.setStato("ACCETTATA");
		prenotazioneRepo.save(prenotazione);
	}
	
	public void rifiutaPrenotazione(Prenotazione prenotazione) {
		prenotazione.setStato("RIFIUTATA");
		prenotazioneRepo.save(prenotazione);
	}
}
