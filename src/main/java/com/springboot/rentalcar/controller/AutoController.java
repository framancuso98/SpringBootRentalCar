package com.springboot.rentalcar.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.rentalcar.entity.Auto;
import com.springboot.rentalcar.exception.NotFoundException;
import com.springboot.rentalcar.service.AutoService;

@RestController
@RequestMapping("/auto")
public class AutoController {

	@Autowired 
	AutoService autoService;
	
	private static final Logger log= LoggerFactory.getLogger(AutoController.class);

	@RequestMapping(value = "/all", method = RequestMethod.GET )
	public ResponseEntity<List<Auto>> listaAuto() throws NotFoundException{
		List<Auto> lista = autoService.list();
		if (lista != null) {
			return new ResponseEntity<List<Auto>>(lista, HttpStatus.OK);
		}else {
			String ErrMsg = String.format("Lista Auto Vuota");
			throw new NotFoundException(ErrMsg);
		}
	}
	
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/elimina/{id}", method = RequestMethod.DELETE)
	public ResponseEntity elimina(@PathVariable int id) {
		Auto auto = autoService.getAuto(id);
		if (auto != null) {
			autoService.deleteById(id);
			return ResponseEntity.ok().body("AUTO ELIMINATA");
		}else {
			log.error("UTENTE NULLO");
			return ResponseEntity.badRequest().body("AUTO NON PRESENTE");
		}
	}
}
