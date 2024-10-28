package com.gnugnes.mercadolibre_challenge_geolocalizacion.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@Table(name = "invocations")
public class Invocation {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String countryCode;

    private Double distance;
}
