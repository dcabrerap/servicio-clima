package com.danielcabrera.clima.web;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import com.danielcabrera.clima.domain.Reporte;
import com.danielcabrera.clima.service.ServicioMetereologico;

public class ControllerServicioMetereologicoTest {
		
	@Test
	public void testConsultarReporte() {
		Reporte reporte = new Reporte();
		ServicioMetereologico servicioMetereologico = mock(ServicioMetereologico.class);
		Model model = mock(Model.class);
		when(servicioMetereologico.procesarPeriodo(100, false)).thenReturn(reporte);
		ControllerServicioMetereologico controllerServicioMetereologico = new ControllerServicioMetereologico(servicioMetereologico);
		Assertions.assertNotNull(controllerServicioMetereologico.consultarReporte("100", model));
	}

}
