package com.springboot.rentalcar.jwt;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.springboot.rentalcar.service.CustomUserDetailsService;

import antlr.collections.List;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClock;

@Component
public class JwtTokenUtil implements Serializable{

	private static final long serialVersionUID = -675898659374127558L;
	static final String CLAIM_KEY_USERNAME = "sub";
	static final String CLAIM_KEY_CREATED = "iat";
	private Clock clock = DefaultClock.INSTANCE;
	
	@Autowired
	private JwtConfig jwtConfig;
	
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}
	
	public Date getIssueAtDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getIssuedAt);
	}
	
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}
	
	
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}
	
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(jwtConfig.getSecret().getBytes()).parseClaimsJws(token).getBody();
	}
	
	
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(clock.now());
	}
	
	private Boolean ignoreTpokenExpiration(String token) {
		return false;
	}
	
	public String generateToken (UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, userDetails);
	}
	
	private String doGenerateToken(Map<String, Object> claims, UserDetails userDetails) {
		
		final Date createdDate = clock.now();
		final Date expirationDate = calculateExpirationDate(createdDate);
		String u = userDetails.getUsername();
		Collection<? extends GrantedAuthority> l =userDetails.getAuthorities();
		return Jwts.builder()
					.setClaims(claims)
					.setSubject(userDetails.getUsername())
					.claim("ruolo", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
					.setIssuedAt(createdDate)
					.setExpiration(expirationDate)
					.signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret().getBytes())
					.compact();
	}
	
	public Boolean canTokenBeRefreshed(String token) {
		return (!isTokenExpired(token) || ignoreTpokenExpiration(token));
	}
	
	public String refreshToken(String token) {
		
		final Date createdDate = clock.now();
		final Date expirationDate = calculateExpirationDate(createdDate); 
		
		final Claims claims = getAllClaimsFromToken(token);
		claims.setIssuedAt(createdDate);
		claims.setExpiration(expirationDate);
		
		return Jwts.builder()
					.setClaims(claims)
					.signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret())
					.compact();
	}

	
	public Boolean validateToken(String token, CustomUserDetailsService userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.loadUserByUsername(CLAIM_KEY_USERNAME).getUsername()) && !isTokenExpired(token));
	}
	
	private Date calculateExpirationDate(Date createdDate) {
		return new Date(createdDate.getTime() + jwtConfig.getExpiration() * 1000);
	}

	
	
	
	
	
	
	
	
}
