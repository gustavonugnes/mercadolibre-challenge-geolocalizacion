package com.gnugnes.mercadolibre_challenge_geolocalizacion.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "invocations")
public class Invocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String countryCode;

    private Double distance;

    private Long amount;
}
