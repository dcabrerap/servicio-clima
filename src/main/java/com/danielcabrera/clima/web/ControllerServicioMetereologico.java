package com.danielcabrera.clima.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.danielcabrera.clima.domain.Clima;
import com.danielcabrera.clima.domain.Reporte;
import com.danielcabrera.clima.service.ServicioMetereologico;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ControllerServicioMetereologico {
	
	//@Autowired
	private final ServicioMetereologico servicioMetereologico;

	@GetMapping("/")
	public String consultarReporte(@RequestParam(name="dias", required=false, defaultValue="3600") String dias,Model model) {
		Reporte reporte = servicioMetereologico.procesarPeriodo(Integer.parseInt(dias), false);
		model.addAttribute("reporte", reporte);
		model.addAttribute("dias", dias);
		return "reporte";	
	}

}
