package com.gnugnes.mercadolibre_challenge_geolocalizacion.controllers;

import com.gnugnes.mercadolibre_challenge_geolocalizacion.services.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/statistics", produces = "application/json; charset=utf-8")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/max")
    public Double getMaxDistance() {
        return statisticsService.getMaxDistance();
    }

    @GetMapping("/min")
    public Double getMinDistance() {
        return statisticsService.getMinDistance();
    }

    @GetMapping("/average")
    public Double getAverageDistance() {
        return statisticsService.getAverageDistance();
    }
}
