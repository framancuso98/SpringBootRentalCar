package com.springboot.rentalcar.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.rentalcar.entity.Utente;

@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService{

	private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

	@Autowired
	UtenteService utenteService;
	
	

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		String ErrMsg = "";

		if (username == null || username.length() < 2) {
			ErrMsg = "Nome utente assente o non valido";

			logger.warn(ErrMsg);

			throw new UsernameNotFoundException(ErrMsg);
		}

		Utente utente = utenteService.findFirstByUsername(username);

		if (utente == null) {

			ErrMsg = String.format("Utente non trovato", username);

			logger.warn(ErrMsg);

		}

		UserBuilder builder = null;
		builder = org.springframework.security.core.userdetails.User.withUsername(username);
		builder.password(utente.getPassword());
		String authorities = utente.getRuolo().getRuolo();
		builder.authorities(authorities);
		return builder.build();
	}
}

	/*private Utente GetHttpValue(String email) {
		URI url = null;

		try {
			String SrvUrl = utenteService.getSrvUrl();

			url = new URI(SrvUrl + email);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor(Config.getPassword()));

		Utente utente = null;

		try {
			utente = restTemplate.getForObject(url, Utente.class);
		} catch (Exception e) {
			String ErrMsg = String.format("Connessione al servizio non riuscite");

			logger.warn(ErrMsg);
		}
		return utente;
	}
}*/

/* Utente utente = utenteService.findFirstByEmail(email);
    System.out.println("before" + user.toString());
      UserBuilder builder = null;
      if (utente != null) {

    	    System.out.println("not null" + utente.getEmail());
    	builder = org.springframework.security.core.userdetails.User.withUsername(email);
        //builder.disabled(!user.isEnabled());
        builder.password(utente.getPassword());
        String authorities = utente.getRuolo().getRuolo();
        builder.authorities(authorities);
        System.out.println("auth"+authorities);
      } else {
    	  System.out.println("utente nullo");
        throw new UsernameNotFoundException("User not found.");
      }
      return builder.build();
  }
}
 */
