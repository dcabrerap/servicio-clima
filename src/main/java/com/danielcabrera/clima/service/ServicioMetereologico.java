package com.danielcabrera.clima.service;

import static com.danielcabrera.clima.domain.Constantes.PLANET_BETASOIDE_ANGULAR_SPEED;
import static com.danielcabrera.clima.domain.Constantes.PLANET_BETASOIDE_SOLAR_DISTANCE;
import static com.danielcabrera.clima.domain.Constantes.PLANET_FERENGI_ANGULAR_SPEED;
import static com.danielcabrera.clima.domain.Constantes.PLANET_FERENGI_SOLAR_DISTANCE;
import static com.danielcabrera.clima.domain.Constantes.PLANET_VULCANO_ANGULAR_SPEED;
import static com.danielcabrera.clima.domain.Constantes.PLANET_VULCANO_SOLAR_DISTANCE;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danielcabrera.clima.domain.Clima;
import com.danielcabrera.clima.domain.Constantes;
import com.danielcabrera.clima.domain.Planeta;
import com.danielcabrera.clima.domain.Reporte;
import com.danielcabrera.clima.repository.ClimaRepository;

@Service
public class ServicioMetereologico {
	
	@Autowired
	private ClimaRepository climaRepository;
	
    @PostConstruct
    public void populateDatabase() {
        this.procesarPeriodo(3600, true);
    }

	public Reporte procesarPeriodo(int numeroDias, boolean populateDatabase) {
		boolean databaseEmpty = climaRepository.count()==0;
		Planeta Ferengi = new Planeta();
		Ferengi.setDistanciaSolar(PLANET_FERENGI_SOLAR_DISTANCE);
		Ferengi.setVelocidadAngular(PLANET_FERENGI_ANGULAR_SPEED);
		Ferengi.setAngulo(0);

		Planeta Betasoide = new Planeta();
		Betasoide.setVelocidadAngular(PLANET_BETASOIDE_ANGULAR_SPEED);
		Betasoide.setDistanciaSolar(PLANET_BETASOIDE_SOLAR_DISTANCE);
		Betasoide.setAngulo(0);

		Planeta Vulcano = new Planeta();
		Vulcano.setVelocidadAngular(PLANET_VULCANO_ANGULAR_SPEED);
		Vulcano.setDistanciaSolar(PLANET_VULCANO_SOLAR_DISTANCE);
		Vulcano.setAngulo(0);

		int diasSequia = 0;
		int diasLLuvia = 0;
		int diasCondicionesOptimas = 0;
		double areaMaxima = 0;
		double area = 0;
		int diaPicoMaximoLLuvia = 0;
		List<Clima> list = new ArrayList<Clima>(); 
		for (int dia = 0; dia < numeroDias; dia++) {
			actualizarPosicion(Ferengi, dia);
			actualizarPosicion(Betasoide, dia);
			actualizarPosicion(Vulcano, dia);
			
			Clima clima = new Clima();
			clima.setDia((long) dia);
			clima.setClima(Constantes.CLIMA_SOLEADO);				

			if (validarPlanetasAlineadosConSol(Ferengi, Betasoide, Vulcano)) {
				diasSequia++;
			}

			if (validarSolEntrePlanetas(Ferengi, Betasoide, Vulcano)) {
				diasLLuvia++;
				clima.setClima(Constantes.CLIMA_LLUVIA);		
			}

			if (validarPlanetasAlineadosEntreSi(Ferengi, Betasoide, Vulcano)) {
				diasCondicionesOptimas++;
				clima.setClima(Constantes.CLIMA_CONDICIONES_OPTIMAS);	
			}

			area = calcularArea(Ferengi, Betasoide, Vulcano);
			if (area > areaMaxima) {
				areaMaxima = area;
				diaPicoMaximoLLuvia = dia;
			}
			if(databaseEmpty)
				list.add(clima);
			
		}
		
		if(databaseEmpty && populateDatabase)
			climaRepository.saveAll(list);
		


		Reporte reporte = new Reporte();
		reporte.setDiaPicoMaximoLLuvia(diaPicoMaximoLLuvia);
		reporte.setDiasCondicionesOptimas(diasCondicionesOptimas);
		reporte.setDiasLLuvia(diasLLuvia);
		reporte.setDiasSequia(diasSequia);
		
	
		return reporte;
	}
	
	public Optional<Clima> climaPorDia(long dia) {
		return climaRepository.findFirstByDia(dia);
	}

	
	private void actualizarPosicion(Planeta planet, int day) {
		double newAngle = day * planet.getVelocidadAngular();
		planet.setAngulo(newAngle);
		planet.setX(planet.getDistanciaSolar() * Math.cos(Math.toRadians(planet.getAngulo())));
		planet.setY(planet.getDistanciaSolar() * Math.sin(Math.toRadians(planet.getAngulo())));
	}

	private double calcularArea(Planeta planet1, Planeta planet2, Planeta planet3) {
		double x1 = planet1.getX();
		double x2 = planet2.getX();
		double x3 = planet3.getX();
		double y1 = planet1.getY();
		double y2 = planet2.getY();
		double y3 = planet3.getY();

		double area = Math.abs((x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2)) / 2);

		return area;
	}

	private boolean validarPlanetasAlineadosConSol(Planeta planet1, Planeta planet2, Planeta planet3) {
		return (validarDosPuntosPasanPorOrigen(planet1.getX(), planet1.getY(), planet2.getX(), planet2.getY())
				&& validarDosPuntosPasanPorOrigen(planet2.getX(), planet2.getY(), planet3.getX(), planet3.getY()));
	}

	private boolean validarDosPuntosPasanPorOrigen(double x1, double y1, double x2, double y2) {
		return (x1 * (y2 - y1) == y1 * (x2 - x1));
	}

	private boolean validarPlanetasAlineadosEntreSi(Planeta planet1, Planeta planet2, Planeta planet3) {
		double area = calcularArea(planet1, planet2, planet3);
		return (area == 0 && !validarPlanetasAlineadosConSol(planet1, planet2, planet3));
	}

	private boolean validarSolEntrePlanetas(Planeta v1, Planeta v2, Planeta v3) {
		double d1, d2, d3;
		boolean has_neg, has_pos;

		d1 = subcalculo(v1, v2);
		d2 = subcalculo(v2, v3);
		d3 = subcalculo(v3, v1);

		has_neg = (d1 < 0) || (d2 < 0) || (d3 < 0);
		has_pos = (d1 > 0) || (d2 > 0) || (d3 > 0);

		return !(has_neg && has_pos);
	}

	private double subcalculo(Planeta p1, Planeta p2) {
		return (0 - p2.getX()) * (p1.getY() - p2.getY()) - (p1.getX() - p2.getX()) * (0 - p2.getY());
	}

}
