package com.gnugnes.mercadolibre_challenge_geolocalizacion.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

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
    private List<LanguageDto> languages;
    private String currencyCode;
    private String currencyName;
    private BigDecimal currencyExchangeRateWithUsDollar;
    private double distanceToBuenosAires;

}
