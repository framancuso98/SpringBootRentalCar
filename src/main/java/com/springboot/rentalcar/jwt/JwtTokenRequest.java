package com.springboot.rentalcar.jwt;

import java.io.Serializable;

public class JwtTokenRequest implements Serializable {

	private static final long serialVersionUID = 2481792041809273686L;
	
	private String username;
	private String password;
	
	
	public JwtTokenRequest() {
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


	@Override
	public String toString() {
		return "JwtTokenRequest [username=" + username + ", password=" + password + "]";
	}
	
	

}
