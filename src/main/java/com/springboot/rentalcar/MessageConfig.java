package com.springboot.rentalcar;


import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class MessageConfig {
	
	@Bean(value = "validator")
	public LocalValidatorFactoryBean validator() {
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		bean.setValidationMessageSource(messageSource());
		return bean;
	}
	
	/*@Bean
	public LocaleResolver localResolver() {
		SessionLocaleResolver sessionLocalResolver = new SessionLocaleResolver();
		sessionLocalResolver.setDefaultLocale(new Locale("it"));
		
		return sessionLocalResolver;
	}*/
	
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource resource = new ResourceBundleMessageSource();
		resource.setBasenames("messeges");
		resource.setUseCodeAsDefaultMessage(true);
		
		return resource;
	}
 
}
