package com.springboot.rentalcar.controller;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.rentalcar.exception.AuthtenticationException;
import com.springboot.rentalcar.jwt.JwtTokenRequest;
import com.springboot.rentalcar.jwt.JwtTokenResponse;
import com.springboot.rentalcar.jwt.JwtTokenUtil;
import com.springboot.rentalcar.service.CustomUserDetailsService;


@RestController
public class JwtAuthenticationRestController {

	@Value("${sicurezza.header}")
	private String tokenHeader;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	private static final Logger logger= LoggerFactory.getLogger(JwtAuthenticationRestController.class);
	
	@PostMapping(value = "${sicurezza.uri}")
	public ResponseEntity<?> cerateAuthenticationToken(@RequestBody JwtTokenRequest authenticationRequest) throws AuthtenticationException{
		
		logger.info("Autenticazione e generazuione token");
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		
		final UserDetails userDetails = userDetailsService
											.loadUserByUsername(authenticationRequest.getUsername());
		
		final String token =jwtTokenUtil.generateToken(userDetails);
		
		logger.info(String.format("Token", token));
		
		return ResponseEntity.ok(token);
	}
	
	
	
	@RequestMapping(value = "${sicurezza.uri}", method = RequestMethod.GET)
	public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request){
		
		String authToken = request.getHeader(tokenHeader);
		final String token = authToken.substring(7);
		
		if (jwtTokenUtil.canTokenBeRefreshed(token)) {
			
			String refreshedToken = jwtTokenUtil.refreshToken(token);
			
			return ResponseEntity.ok(new JwtTokenResponse(refreshedToken));	
		}else { 
			return ResponseEntity.badRequest().body(null);
		}
	}
	
	
	@ExceptionHandler({AuthtenticationException.class})
	public ResponseEntity<String> handleAuthenticationException(AuthtenticationException e){
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
	}
	
	
	private void authenticate(String username, String password) {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);
		
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			logger.warn("UTENTE DISABILITATO");
			throw new AuthtenticationException("UTENTE DISBILITATO", e );
		}catch (BadCredentialsException e) {
			logger.warn("CREDENZIALI NON VALIDE");
			throw new BadCredentialsException("CREDENZIALI NON VALIDE ", e);
		}
	}
	 
}











