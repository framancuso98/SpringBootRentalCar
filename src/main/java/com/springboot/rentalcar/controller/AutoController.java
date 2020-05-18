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

import com.google.gson.Gson;
import com.springboot.rentalcar.entity.Auto;
import com.springboot.rentalcar.exception.NotFoundException;
import com.springboot.rentalcar.service.AutoService;

@RestController
@RequestMapping("/auto")
public class AutoController {

	@Autowired 
	AutoService autoService;
	
	@Autowired
	Gson gson;

	private static final Logger log= LoggerFactory.getLogger(AutoController.class);

	@RequestMapping(value = "/all", method = RequestMethod.GET )
	public ResponseEntity<List<Auto>> listaAuto() throws NotFoundException{
		try {
			List<Auto> lista = autoService.list();
			if (lista != null) {
				return new ResponseEntity<List<Auto>>(lista, HttpStatus.OK);
			}else {
				//String ErrMsg = String.format("Lista Auto Vuota");
				return new ResponseEntity<List<Auto>>(HttpStatus.BAD_REQUEST);
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
				Gson gson = new Gson();
				String msg = gson.toJson("AUTO ELIMINATA");
				return ResponseEntity.ok().body(msg);
			}else {
				log.error("UTENTE NULLO");
				Gson gson = new Gson();
				String msg = gson.toJson("UTENTE NULLO!");
				return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Gson gson = new Gson();
			String msg = gson.toJson("IMPOSSIBILE ELIMINARE L'AUTO");
			return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
		}
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/inserisci", method = RequestMethod.POST)
	public ResponseEntity iserisci(@RequestBody Auto auto) {
		try {
			System.out.println(auto);
			if (autoService.existsByTarga(auto.getTarga())) {
				log.error("AUTO INESISTENTE!!");
				Gson gson = new Gson();
				String msg = gson.toJson("AUTO INESISTENTE!!");
				return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
			}else {
				autoService.addAuto(auto);
				log.info("AUTO AGGIUNTA CON SUCCESSO");
				Gson gson = new Gson();
				String msg = gson.toJson("AUTO AGGIUNTA CON SUCCESSO");
				return ResponseEntity.ok().body(msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Gson gson = new Gson();
			String msg = gson.toJson("IMPOSSIBILE INSERIRE L'AUTO");
			return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
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
				Gson gson = new Gson();
				String msg = gson.toJson("TARGA NON DISPONIBILE");
				return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
			}else {
				auto.setCostruttore(newAuto.getCostruttore());
				auto.setModello(newAuto.getModello());
				auto.setAnno(newAuto.getAnno());
				auto.setTarga(newAuto.getTarga().toUpperCase());
				auto.setCategoria(newAuto.getCategoria());
				autoService.addAuto(auto);
				Gson gson = new Gson();
				String msg = gson.toJson("AUTO MODIFICATA CON SUCCESSO");
				return ResponseEntity.ok().body(msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Gson gson = new Gson();
			String msg = gson.toJson("IMPOSSIBILE MODIFICARE L'AUTO");
			return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
		}
	}
}
