package com.danielcabrera.clima.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Planeta {
	private String nombre;
	private Long distanciaSolar;
	private Long velocidadAngular;
	private double angulo;
	private double x;
	private double y;
}
