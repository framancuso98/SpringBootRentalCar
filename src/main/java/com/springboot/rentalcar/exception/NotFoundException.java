package com.springboot.rentalcar.exception;

public class NotFoundException extends Exception{

	private static final long serialVersionUID = 106854245652969952L;
	
	private String  messaggio = "Elemento ricercato non trovato";

	public NotFoundException(String Messaggio) {
		super(Messaggio);
		this.messaggio = Messaggio;
	}

	public String getMessaggio() {
		return messaggio;
	}

	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}

}
