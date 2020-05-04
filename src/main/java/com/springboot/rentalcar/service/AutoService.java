package com.springboot.rentalcar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.rentalcar.entity.Auto;
import com.springboot.rentalcar.repository.AutoRepository;
@Service
public class AutoService {

	@Autowired
	AutoRepository autoRepo;
	
	public List<Auto> list(){
		return autoRepo.findAll();
	}
	
	public void  deleteById(Integer id) {
		autoRepo.deleteById(id);
	}
	
	public Auto findFirstById(int id) {
		return autoRepo.findFirstById(id);
	}
	
	public boolean existsByTarga(String targa) {
		return autoRepo.existsByTarga(targa);
	}
	
	public void addAuto(Auto auto) {
		autoRepo.save(auto);
	}
}
