package com.springboot.rentalcar.controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class Prova { 
	
	

	@GetMapping(value = "/prova")
	public String getProva(ModelMap model) {
		model.addAttribute("ciao", "ciao");
		return "ciaoooooo";
	}
}
