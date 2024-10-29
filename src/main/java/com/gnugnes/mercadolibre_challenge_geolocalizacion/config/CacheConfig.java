package com.gnugnes.mercadolibre_challenge_geolocalizacion.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CacheConfig {

    // Task scheduled to runs at midnight every day and clear all redis caches
    @Scheduled(cron = "0 0 0 * * ?")
    @CacheEvict(value = {"max_distance", "min_distance", "average_distance"}, allEntries = true)
    public void clearAllCaches() {
        log.info("All entries cleared for caches: max_distance, min_distance, average_distance");
    }
}
