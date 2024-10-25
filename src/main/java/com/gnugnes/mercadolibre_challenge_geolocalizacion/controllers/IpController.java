package com.gnugnes.mercadolibre_challenge_geolocalizacion.controllers;

import com.gnugnes.mercadolibre_challenge_geolocalizacion.dtos.Ip2CountryDto;
import com.gnugnes.mercadolibre_challenge_geolocalizacion.services.Ip2CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/ips", produces = "application/json; charset=utf-8")
@RequiredArgsConstructor
public class IpController {

    private final Ip2CountryService ip2CountryService;

    @GetMapping("/{ip}")
    public Ip2CountryDto getIpData(@PathVariable String ip) {
        return ip2CountryService.getCountryDataFake(ip);
    }
}
