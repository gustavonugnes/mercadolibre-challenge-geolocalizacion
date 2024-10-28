package com.gnugnes.mercadolibre_challenge_geolocalizacion.services;

import com.gnugnes.mercadolibre_challenge_geolocalizacion.dtos.ExchangeRatesDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class UtilsServiceTest {

    @InjectMocks
    private UtilsService utilsService;
    private static final double EARTH_RADIUS = 6371.0; // Earth's radius in kilometers

    @Test
    void testCalculateDistance_sameLocation() {
        double lat = 40.7128;
        double lon = -74.0060; // New York coordinates
        double result = utilsService.calculateDistance(lat, lon, lat, lon);

        assertThat(result).isZero();
    }

    @Test
    void testCalculateDistance_differentLocations() {
        double lat1 = 40.7128; // New York
        double lon1 = -74.0060;
        double lat2 = 34.0522; // Los Angeles
        double lon2 = -118.2437;

        double result = utilsService.calculateDistance(lat1, lon1, lat2, lon2);

        assertThat(result).isEqualTo(3978.19353303554);
    }

    @Test
    void testGetDistanceFromBuenosAires() {
        double lat1 = -34.61315; // Buenos Aires
        double lon1 = -58.37723;
        double lat2 = 34.0522; // Los Angeles
        double lon2 = -118.2437;

        double result = utilsService.calculateDistance(lat1, lon1, lat2, lon2);

        assertThat(result).isEqualTo(10129.631176056328);
    }

    @Test
    void getDollarExchangeRate() {
        var exchangeRatesDto = new ExchangeRatesDto();
        exchangeRatesDto.setBase("EUR");

        var rates = Map.of(
                "USD", new BigDecimal("1.079971"),
                "ARS", new BigDecimal("1063.117081")
        );

        exchangeRatesDto.setRates(rates);

        var result = utilsService.getDollarExchangeRate(exchangeRatesDto, "ARS");

        assertThat(result).isEqualTo(new BigDecimal("0.001016"));
    }
}