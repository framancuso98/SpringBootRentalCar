package com.springboot.rentalcar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class SpringBootRentalCarApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRentalCarApplication.class, args);
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		
		return new WebMvcConfigurer() {
			
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry
					.addMapping("/**")
					.allowedOrigins("http://localhost:4200")
					.allowedMethods("PUT", "DELETE", "GET", "POST", "OPTIONS", "HEAD", "PATCH")
					.allowedHeaders("*")
					.exposedHeaders("header1", "header2", "Authorization")
					.allowCredentials(true)
					.maxAge(3600);
				
			}
		};
	}

}
