package com.danielcabrera.clima.rest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.danielcabrera.clima.domain.Clima;
import com.danielcabrera.clima.service.ServicioMetereologico;

public class ServicioClimaRestTest {
	
	@Test
	public void testConsultarClimaPorDia() {
		ServicioMetereologico servicioMetereologico = mock(ServicioMetereologico.class);
		ServicioClimaRest servicioClimaRest = new ServicioClimaRest(servicioMetereologico);
		when(servicioMetereologico.climaPorDia(100)).thenReturn(Optional.of(new Clima()));
		Assertions.assertNotNull(servicioClimaRest.consultarClimaPorDia("100"));
	}

}
