package com.gnugnes.mercadolibre_challenge_geolocalizacion.services;

import com.gnugnes.mercadolibre_challenge_geolocalizacion.dtos.Ip2CountryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IpService {

    private final Ip2CountryService ip2CountryService;

    public Ip2CountryDto getCountryData(String ip) {
        return ip2CountryService.getCountryDataFake(ip);
    }
}
