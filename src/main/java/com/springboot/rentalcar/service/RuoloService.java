package com.springboot.rentalcar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.rentalcar.entity.Ruolo;
import com.springboot.rentalcar.repository.RuoloRepository;

@Service
public class RuoloService {

	@Autowired
	RuoloRepository ruoloRepo;
	
	public Ruolo findFirstById(int Id) {
		return ruoloRepo.findFirstById(Id);
	}
}
