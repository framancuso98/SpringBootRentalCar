package com.springboot.rentalcar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.rentalcar.service.PrenotazioneService;

@RestController
public class PrenotazioneController {
	@Autowired
	PrenotazioneService prenotazioneService;
}
