package com.danielcabrera.clima.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.danielcabrera.clima.domain.Clima;

public interface ClimaRepository extends JpaRepository<Clima, Long> {
	
	Optional<Clima> findFirstByDia(Long dia);
}
