package com.springboot.rentalcar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.rentalcar.entity.Prenotazione;
import com.springboot.rentalcar.entity.Utente;

@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Integer> {

	public Prenotazione findFirstByUtente(Utente utente);
}
