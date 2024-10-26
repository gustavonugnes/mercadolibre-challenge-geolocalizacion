package com.gnugnes.mercadolibre_challenge_geolocalizacion.services;

import com.gnugnes.mercadolibre_challenge_geolocalizacion.Utils;
import com.gnugnes.mercadolibre_challenge_geolocalizacion.dtos.CountryDto;
import com.gnugnes.mercadolibre_challenge_geolocalizacion.dtos.LanguageDto;
import com.gnugnes.mercadolibre_challenge_geolocalizacion.entities.Invocation;
import com.gnugnes.mercadolibre_challenge_geolocalizacion.external.Ip2CountryService;
import com.gnugnes.mercadolibre_challenge_geolocalizacion.repositories.InvocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class IpService {

    private final Ip2CountryService ip2CountryService;
    private final InvocationRepository invocationRepository;

    public CountryDto getCountryData(String ip) {
        var data = ip2CountryService.getCountryDataFake(ip);
//        var data = ip2CountryService.getCountryData(ip);

        var countryDto = new CountryDto();
        countryDto.setIp(data.getIp());
        countryDto.setLocalDateTime(LocalDateTime.now());
        countryDto.setName(data.getCountryName());
        countryDto.setIsoCode(data.getCountryCode());

        if (data.getLocation() != null && data.getLocation().getLanguages() != null) {
            countryDto.setLanguages(
                    data.getLocation().getLanguages().stream().map(each -> {
                        var languageDto = new LanguageDto();
                        languageDto.setName(each.getName());
                        languageDto.setCode(each.getCode());
                        languageDto.setNativeName(each.getNativeName());
                        return languageDto;
                    }).toList());
        }

        /* Originally I wanted to get the currency of the country via the "IP2Country (IpApi)" service.
        However, the free plan I have does not return the currency data. (Requires a paid plan)
        So I implemented a workaround using just Java code, as you can see in the Utils class.
        */
        var currency = Utils.getCurrencyByCountryCode(data.getCountryCode());

        countryDto.setCurrencyCode(currency.getCurrencyCode());
        countryDto.setCurrencyName(currency.getDisplayName());
        countryDto.setCurrencyExchangeRateWithUsDollar(null);


        countryDto.setTimeZones(Utils.getCurrentTimesByCountry(data.getCountryCode()));


        countryDto.setDistanceToBuenosAires(Utils.distanceFromBuenosAires(data.getLatitude(), data.getLongitude()));

        Invocation invocation = invocationRepository.findByCountryCode(data.getCountryCode());
        if (invocation == null) {
            invocation = new Invocation();
            invocation.setAmount(0L);
        }

        invocation.setCountryCode(data.getCountryCode());
        invocation.setDistance(countryDto.getDistanceToBuenosAires());
        invocation.setAmount(invocation.getAmount() + 1);

        invocationRepository.save(invocation);

        return countryDto;
    }
}
