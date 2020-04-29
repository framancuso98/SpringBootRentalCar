/*package com.springboot.rentalcar.config;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import com.springboot.rentalcar.exception.AuthtenticationException;




public class AuthEntryPoint extends BasicAuthenticationEntryPoint{

	private static final Logger logger = LoggerFactory.getLogger(AuthEntryPoint.class);
	
	private static String REALM = "REAME";
	
	
	public void commence (final HttpServletRequest request, final HttpServletResponse response,
			final AuthtenticationException authExeption) throws IOException, ServletException{
		
		String ErrMsg = "Username e/o password non corrette!";
		
		logger.warn("Errore Sicurezza: "+ authExeption.getMessage());
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.addHeader("WWW_Authenticate", "Basic realm= " +getRealmName());
		
		PrintWriter writer = response.getWriter();
		writer.println(ErrMsg);
	}
	
	@Override
	public void afterPropertiesSet(){
		setRealmName(REALM);
		super.afterPropertiesSet();
		
	}
}
*/