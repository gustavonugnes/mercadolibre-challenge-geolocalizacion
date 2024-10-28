package com.gnugnes.mercadolibre_challenge_geolocalizacion.services;

import com.gnugnes.mercadolibre_challenge_geolocalizacion.repositories.InvocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final InvocationRepository invocationRepository;

    @Cacheable(value = "max_distance")
    public Double getMaxDistance() {
        return invocationRepository.findMaxDistance();
    }

    @Cacheable(value = "min_distance")
    public Double getMinDistance() {
        return invocationRepository.findMinDistance();
    }

    @Cacheable(value = "average_distance")
    public Double getAverageDistance() {
        return invocationRepository.findAverageDistance();
    }
}
