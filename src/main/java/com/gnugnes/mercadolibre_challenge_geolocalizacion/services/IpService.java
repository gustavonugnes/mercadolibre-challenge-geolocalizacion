package com.gnugnes.mercadolibre_challenge_geolocalizacion.services;

import com.gnugnes.mercadolibre_challenge_geolocalizacion.Utils;
import com.gnugnes.mercadolibre_challenge_geolocalizacion.dtos.CountryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class IpService {

    private final Ip2CountryService ip2CountryService;

    public CountryDto getCountryData(String ip) {
//        var data = ip2CountryService.getCountryDataFake(ip);
        var data = ip2CountryService.getCountryData(ip);

        var countryDto = new CountryDto();
        countryDto.setIp(data.getIp());
        countryDto.setLocalDateTime(LocalDateTime.now());
        countryDto.setName(data.getCountryName());
        countryDto.setIsoCode(data.getCountryCode());

        if(data.getLocation() != null && data.getLocation().getLanguages() != null) {
            var firstLanguage = data.getLocation().getLanguages().getFirst();
            countryDto.setLanguage(firstLanguage.getName());
            countryDto.setLanguage(firstLanguage.getCode());
        }

        countryDto.setCurrency(null);
        countryDto.setCurrencyExchangeRateWithUsDollar(null);
        countryDto.setCountryTime(null);
        countryDto.setDistanceToBuenosAires(Utils.distanceFromBuenosAires(data.getLatitude(), data.getLongitude()));


        return countryDto;
    }
}
