package com.springboot.rentalcar.jwt;

import java.io.Serializable;

public class JwtTokenResponse implements Serializable {

	private static final long serialVersionUID = 1746869007820454143L;


	private String token;


	public JwtTokenResponse(String token) {
	}


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}


	@Override
	public String toString() {
		return "JwtTokenResponse [token=" + token + "]";
	}
	
	

}
