package com.springboot.rentalcar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.rentalcar.entity.Auto;
import com.springboot.rentalcar.entity.Prenotazione;
import com.springboot.rentalcar.entity.Utente;

@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Integer> {

	public List<Prenotazione> findFirstByUtente(Utente utente);
	
	public Prenotazione findFirstById(int id);
	
	public List<Prenotazione> findFirstByAuto(Auto auto);
}
