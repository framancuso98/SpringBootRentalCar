package com.springboot.rentalcar.entity;


import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@Table(name = "ruolo_tbl")
public class Ruolo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int id;
	
	@Column(name = "ruolo")
	private String ruolo;
	
	@OneToMany(mappedBy="ruolo", fetch = FetchType.EAGER)
	@JsonManagedReference
	private List<Utente> utentes;

	public Ruolo() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public List<Utente> getUtentes() {
		return utentes;
	}

	public void setUtentes(List<Utente> utentes) {
		this.utentes = utentes;
	}

	@Override
	public String toString() {
		return "Ruolo [id=" + id + ", ruolo=" + ruolo + ", utentes=" + utentes + "]";
	}

}

