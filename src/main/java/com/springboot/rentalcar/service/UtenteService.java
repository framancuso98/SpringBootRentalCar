package com.springboot.rentalcar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.rentalcar.entity.Utente;
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
	
	public Utente findFirstByUsername(String username) {
		return utenteRepo.findFirstByUsername(username);
	}
	
	public boolean existsByUsername(String username) {
		return utenteRepo.existsByUsername(username);
	}
	
	public void  deleteById(Integer id) {
		utenteRepo.deleteById(id);
	}
	
	public void addUtente(Utente utente) {
		utenteRepo.save(utente);
	}
		
}
