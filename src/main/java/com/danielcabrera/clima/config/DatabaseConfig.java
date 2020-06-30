package com.danielcabrera.clima.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

import com.danielcabrera.clima.service.ServicioMetereologico;

public class DatabaseConfig {
	
	@Bean
	@DependsOn("liquibase")
	public ServicioMetereologico servicioMetereologico() {
	    return new ServicioMetereologico();
	}
}
