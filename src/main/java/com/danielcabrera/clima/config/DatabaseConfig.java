package com.danielcabrera.clima.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import com.danielcabrera.clima.service.ServicioMetereologico;

@Configuration
public class DatabaseConfig {
	
	@Value( "${spring.datasource.driver-class-name}" )
	private String className;
	
	@Value( "${spring.datasource.url}" )
	private String url;
	
	@Value( "${spring.datasource.username}" )
	private String user;
	
	@Value( "${spring.datasource.password}" )
	private String password;

    @Bean
    public DataSource datasource() {
        return DataSourceBuilder.create()
          .driverClassName(className)
          .url(url)
          .username(user)
          .password(password)
          .build(); 
    }

}
