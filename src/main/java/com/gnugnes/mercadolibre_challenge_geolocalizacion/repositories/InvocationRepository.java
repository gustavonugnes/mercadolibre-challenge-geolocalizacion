package com.gnugnes.mercadolibre_challenge_geolocalizacion.repositories;

import com.gnugnes.mercadolibre_challenge_geolocalizacion.entities.Invocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InvocationRepository extends JpaRepository<Invocation, Long> {

    Invocation findByCountryCode(String countryCode);

    @Query(value = "SELECT MAX(distance) FROM Invocation")
    Double findMaxDistance();

    @Query(value = "SELECT MIN(distance) FROM Invocation")
    Double findMinDistance();

    @Query(nativeQuery = true, value =
            """
            SELECT SUM(distance * amount) / SUM(amount)
            FROM invocations
            """
    )
    Double findAverageDistance();
}
