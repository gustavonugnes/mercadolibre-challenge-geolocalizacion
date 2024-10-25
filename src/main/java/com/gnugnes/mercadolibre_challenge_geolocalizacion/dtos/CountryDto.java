package com.gnugnes.mercadolibre_challenge_geolocalizacion.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CountryDto {

    private String ip;
    private LocalDateTime localDateTime;
    private Instant countryTime;
    private String name;
    private String isoCode;
    private String language;
    private String languageCode;
    private String currency;
    private double distanceToBuenosAires;

}
