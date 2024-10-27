package com.gnugnes.mercadolibre_challenge_geolocalizacion.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CountryDto {

    private String ip;
    private LocalDateTime localDateTime;
    private Set<String> timeZones;
    private String name;
    private String isoCode;
    private List<LanguageDto> languages;
    private String currencyCode;
    private String currencyName;
    private BigDecimal currencyExchangeRateWithUsDollar;
    private Double latitude;
    private Double longitude;
    private Double buenosAiresLatitude;
    private Double buenosAiresLongitude;
    private Double distanceToBuenosAires;


}
