package com.danielcabrera.clima.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.danielcabrera.clima.service.ServicioMetereologico;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ServicioClimaRest {
	
	private final ServicioMetereologico servicioMetereologico;

	@GetMapping("/clima")
	public ResponseEntity consultarClimaPorDia(@RequestParam(name="dia", required=true) String dia) {
		Optional response = servicioMetereologico.climaPorDia(Integer.parseInt(dia));
		if(response.isPresent()) 
			return new ResponseEntity<>(response.get(), HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
