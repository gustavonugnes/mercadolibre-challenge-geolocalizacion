package com.gnugnes.mercadolibre_challenge_geolocalizacion.repositories;

import com.gnugnes.mercadolibre_challenge_geolocalizacion.entities.Invocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvocationRepository extends JpaRepository<Invocation, Long> {

    Invocation findByCountryCode(String countryCode);
}
