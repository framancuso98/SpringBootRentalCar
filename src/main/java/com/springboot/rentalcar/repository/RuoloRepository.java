package com.springboot.rentalcar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.rentalcar.model.Ruolo;

@Repository
public interface RuoloRepository extends JpaRepository<Ruolo, Integer> {

}