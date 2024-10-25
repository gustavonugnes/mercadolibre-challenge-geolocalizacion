package com.gnugnes.mercadolibre_challenge_geolocalizacion.controllers;

import com.gnugnes.mercadolibre_challenge_geolocalizacion.dtos.CountryDto;
import com.gnugnes.mercadolibre_challenge_geolocalizacion.dtos.Ip2CountryDto;
import com.gnugnes.mercadolibre_challenge_geolocalizacion.services.Ip2CountryService;
import com.gnugnes.mercadolibre_challenge_geolocalizacion.services.IpService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/ips", produces = "application/json; charset=utf-8")
@RequiredArgsConstructor
public class IpController {

    private final IpService ipService;

    @GetMapping("/{ip}")
    public CountryDto getIpData(@PathVariable String ip) {
        return ipService.getCountryData(ip);
    }
}
