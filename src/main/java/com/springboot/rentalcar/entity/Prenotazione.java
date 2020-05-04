package com.springboot.rentalcar.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "prenotazione_tbl")
public class Prenotazione implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int id;
	
	@Column(name = "stato")
	private String stato;
	
	@ManyToOne( fetch = FetchType.EAGER)
	@JoinColumn(name="id_auto", nullable=false)
	private Auto auto;

	//bi-directional many-to-one association to User
	@ManyToOne( fetch = FetchType.EAGER)
	@JoinColumn(name="id_utente", nullable=false)
	private Utente utente;

	public Prenotazione() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public Auto getAuto() {
		return auto;
	}

	public void setAuto(Auto auto) {
		this.auto = auto;
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	@Override
	public String toString() {
		return "Prenotazione [id=" + id + ", stato=" + stato + ", auto=" + auto + ", utente=" + utente + "]";
	}
}
