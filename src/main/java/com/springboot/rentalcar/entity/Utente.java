package com.springboot.rentalcar.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "utente_tbl")
public class Utente implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Integer id;
	
	@NotEmpty
	@Column(name = "username")
	private String username;
	
	@NotEmpty
	@Column(name = "password")
	private String password;

	@NotEmpty
	@Column(name = "nome")
	private String nome;
	
	@NotEmpty
	@Column(name = "cognome")
	private String cognome;
	
	@NotEmpty
	@Column(name = "data_nascita")
	private String data_nascita;
	
	@ManyToOne( fetch = FetchType.EAGER)
	@JoinColumn(name ="ruolo_id")
	//@JsonBackReference
	private Ruolo ruolo;
	
	//bi-directional many-to-one association to PrenotazioneTbl
	@OneToMany(mappedBy="utente")
	//@JsonBackReference
	@JsonIgnore
	private List<Prenotazione> prenotaziones;


	public Utente() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getData_nascita() {
		return data_nascita;
	}

	public void setData_nascita(String data_nascita) {
		this.data_nascita = data_nascita;
	}

	public Ruolo getRuolo() {
		return ruolo;
	}

	public void setRuolo(Ruolo ruolo) {
		this.ruolo = ruolo;
	}

	@Override
	public String toString() {
		return "Utente [id=" + id + ", username=" + username + ", password=" + password + ", nome=" + nome + ", cognome="
				+ cognome + ", data_nascita=" + data_nascita + ", ruolo=" + ruolo + "]";
	}
}
