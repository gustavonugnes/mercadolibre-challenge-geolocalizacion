package com.gnugnes.mercadolibre_challenge_geolocalizacion.services;

import com.gnugnes.mercadolibre_challenge_geolocalizacion.repositories.InvocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final InvocationRepository invocationRepository;

    public Double getMaxDistance() {
        return invocationRepository.findMaxDistance();
    }

    public Double getMinDistance() {
        return invocationRepository.findMinDistance();
    }

    public Double getAverageDistance() {
        return invocationRepository.findAverageDistance();
    }
}
