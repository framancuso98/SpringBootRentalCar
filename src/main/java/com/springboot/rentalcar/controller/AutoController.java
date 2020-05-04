package com.springboot.rentalcar.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
		try {
			List<Auto> lista = autoService.list();
			if (lista != null) {
				return new ResponseEntity<List<Auto>>(lista, HttpStatus.OK);
			}else {
				String ErrMsg = String.format("Lista Auto Vuota");
				throw new NotFoundException(ErrMsg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<Auto>>(HttpStatus.BAD_REQUEST);
		}
	}


	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/elimina/{id}", method = RequestMethod.DELETE)
	public ResponseEntity elimina(@PathVariable int id) {
		try {
			Auto auto = autoService.findFirstById(id);
			if (auto != null) {
				autoService.deleteById(id);
				return ResponseEntity.ok().body("AUTO ELIMINATA");
			}else {
				log.error("UTENTE NULLO");
				return ResponseEntity.badRequest().body("AUTO NON PRESENTE");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body("IMPOSSIBILE ELIMINARE L'AUTO");
		}
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/inserisci", method = RequestMethod.PUT)
	public ResponseEntity iserisci(@RequestBody Auto auto) {
		try {
			System.out.println(auto);
			if (autoService.existsByTarga(auto.getTarga())) {
				log.error("AUTO INESISTENTE!!");
				return ResponseEntity.badRequest().body("AUTO INESISTENTE!!");
			}else {
				autoService.addAuto(auto);
				log.info("AUTO AGGIUNTA CON SUCCESSO");
				return ResponseEntity.ok().body("AUTO AGGIUNTA CON SUCCESSO");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body("IMPOSSIBILE INSERIRE L'AUTO");
		}
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/modifica", method = RequestMethod.PUT)
	public ResponseEntity modificaAuto(@RequestBody Auto newAuto) {
		try {
			int id_auto = newAuto.getId();
			Auto auto = autoService.findFirstById(id_auto);
			if (autoService.existsByTarga(newAuto.getTarga()) && !newAuto.getTarga().equalsIgnoreCase(auto.getTarga()) ) {
				log.info("TARGA GIA PRESENTE");
				return ResponseEntity.badRequest().body("TARGA NON DISPONIBILE");
			}else {
				auto.setCostruttore(newAuto.getCostruttore());
				auto.setModello(newAuto.getModello());
				auto.setAnno(newAuto.getAnno());
				auto.setTarga(newAuto.getTarga());
				auto.setCategoria(newAuto.getCategoria());
				autoService.addAuto(auto);
				return ResponseEntity.ok().body("AUTO MODIFICATA CON SUCCESSO");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body("IMPOSSIBILE MODIFICARE L'AUTO");
		}
	}
}
