package com.gnugnes.mercadolibre_challenge_geolocalizacion.repositories;

import com.gnugnes.mercadolibre_challenge_geolocalizacion.entities.Invocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InvocationRepository extends JpaRepository<Invocation, Long> {

    @Query(value = "SELECT MAX(distance) FROM Invocation")
    Double findMaxDistance();

    @Query(value = "SELECT MIN(distance) FROM Invocation")
    Double findMinDistance();

    @Query(nativeQuery = true, value =
            """
            SELECT MAX(total_distance / invocation_count)
            FROM (
              SELECT
                SUM(distance) AS total_distance,
                (
                  SELECT COUNT(*)
                  FROM invocations
                )
                  AS invocation_count
              FROM invocations
              GROUP BY country_code
            )
              AS max_distance;
            """
    )
    Double findAverageDistance();
}
