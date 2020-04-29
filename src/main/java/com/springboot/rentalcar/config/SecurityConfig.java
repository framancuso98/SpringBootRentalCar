/*package com.springboot.rentalcar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

package com.springboot.rentalcar.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.springboot.rentalcar.service.CustomUserDetailsService;



@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserAuthenticationSuccessHandler successHandler;


	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	};

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()
		.authorizeRequests()
		//.antMatchers("/").permitAll()
		//.antMatchers("/auto").hasRole("ADMIN")
		//.antMatchers("/anonymous*").anonymous()
		.antMatchers("/login*").permitAll()
		.antMatchers("/utente*").permitAll()
		.anyRequest().permitAll()
		.and()
		.formLogin()
		.loginPage("/login")
		//.defaultSuccessUrl("/login", true)
		//.successHandler(successHandler)
		//.loginProcessingUrl("/login")
		.failureUrl("/login?error=true")
		//.failureHandler(authenticationFailureHandler());
		.and()
		.logout()
		.logoutUrl("/perform_logout")
		.deleteCookies("JSESSIONID");
		//.logoutSuccessHandler(logoutSuccessHandler());
	}
}



@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	private static String REALM = "REAME";
	
	
	@Bean
	public BCryptPasswordEncoder passwordEncode() {
		return new BCryptPasswordEncoder();
	};
	
	
	@Bean
	public UserDetailsService userDetailsService() {
		
		UserBuilder users = User.builder();
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
		
		manager.createUser(users.username("Francesco")
				.password(new BCryptPasswordEncoder().encode("ciao"))
				.roles("USER")
				.build());
		
		manager.createUser(users.username("Admin")
				.password(new BCryptPasswordEncoder().encode("admin"))
				.roles("USER", "ADMIN")
				.build());
		
		return manager;
	}
	
	private static final String[] USER_MATCHER = {"/api/auto/cerca/**"};
	private static final String[] ADMIN_MATCHER = {"/api/auto/inserisci/**",
			"api/auto/modifica", "api/auto/elimina/**"};

	
	@Override
	protected void configure (HttpSecurity http) throws Exception {
		
		http
			.csrf().disable()
			.authorizeRequests()
			.antMatchers(USER_MATCHER).hasRole("USER")
			.antMatchers(ADMIN_MATCHER).hasRole("ADMIN")
			.anyRequest().authenticated()
			.and()
            	.oauth2ResourceServer()
            	.jwt();

			.and()	
				.httpBasic().realmName(REALM).authenticationEntryPoint(getBasicEntryPoint())
			.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	@Bean
	public AuthEntryPoint getBasicEntryPoint() {
		return new AuthEntryPoint();
				
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception{
		web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
	}
	
}
	
	
*/

