package com.springboot.rentalcar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.rentalcar.model.Utente;
@Repository
public interface UtenteRepository extends JpaRepository<Utente, Integer>{

	public Utente findFirstByEmail(String email);
	
	public Utente findFirstById(int id);
}

