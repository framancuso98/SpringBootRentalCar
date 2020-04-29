package com.springboot.rentalcar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.rentalcar.entity.Utente;
@Repository
public interface UtenteRepository extends JpaRepository<Utente, Integer>{

	public Utente findFirstByUsername(String username);
	
	public Utente findFirstById(int id);
}

