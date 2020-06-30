package com.danielcabrera.clima.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Reporte {
	private int diasSequia;
	private int diasLLuvia;
	private int diaPicoMaximoLLuvia;
	private int diasCondicionesOptimas;
}
