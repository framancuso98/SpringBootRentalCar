package com.springboot.rentalcar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.rentalcar.model.Utente;
import com.springboot.rentalcar.repository.UtenteRepository;
@Service
public class UtenteService {

	@Autowired
	UtenteRepository utenteRepo;
	
	
	public List<Utente> list(){
		return utenteRepo.findAll();
	}
	
	public Utente getUtente(int id) {
		return utenteRepo.findFirstById(id);
	}
	
	public Utente findFirstByEmail(String email) {
		return utenteRepo.findFirstByEmail(email);
	}
}
