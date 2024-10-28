package com.gnugnes.mercadolibre_challenge_geolocalizacion.controllers;

import com.gnugnes.mercadolibre_challenge_geolocalizacion.dtos.CountryDto;
import com.gnugnes.mercadolibre_challenge_geolocalizacion.services.IpService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.InetAddressValidator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequestMapping(path = "/ips", produces = "application/json; charset=utf-8")
@RequiredArgsConstructor
public class IpController {

    private final IpService ipService;

    @GetMapping("/{ip}")
    public CountryDto getIpData(@PathVariable String ip) {
        if(!InetAddressValidator.getInstance().isValid(ip)) {
            throw new ResponseStatusException(BAD_REQUEST, "The provided IP does not have a valid format");
        }

        return ipService.getCountryData(ip);
    }
}
