package com.danielcabrera.clima.service;



import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import com.danielcabrera.clima.service.ServicioMetereologico;


public class ServicioMetereologicoTest {
	
	ServicioMetereologico servicioMetereologico;
	
	@Test
	public void procesarPeriodoTest() {
		servicioMetereologico = new ServicioMetereologico();
		Assertions.assertThat(servicioMetereologico.procesarPeriodo(100, false)).isNotNull();
	}

}
