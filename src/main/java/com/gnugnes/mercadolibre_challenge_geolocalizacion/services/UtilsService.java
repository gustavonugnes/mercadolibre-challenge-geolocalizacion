package com.gnugnes.mercadolibre_challenge_geolocalizacion.services;

import com.gnugnes.mercadolibre_challenge_geolocalizacion.dtos.ExchangeRatesDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Currency;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import static java.math.RoundingMode.UP;
import static java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;

@Service
public class UtilsService {

    public static final double BUENOS_AIRES_LAT = -34.61315;
    public static final double BUENOS_AIRES_LON = -58.37723;

    private static final double EARTH_RADIUS = 6371;
    private static final String USD_CODE = "USD";

    public Double getDistanceFromBuenosAires(Double lat, Double lon) {
        return calculateDistance(BUENOS_AIRES_LAT, BUENOS_AIRES_LON, lat, lon);
    }

    // Uses the Equirectangular Distance Approximation
    // Not extremely accurate but very fast, so it should be enough for the purposes of this application
    public Double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double lat1Rad = Math.toRadians(lat1);
        double lat2Rad = Math.toRadians(lat2);
        double lon1Rad = Math.toRadians(lon1);
        double lon2Rad = Math.toRadians(lon2);

        double x = (lon2Rad - lon1Rad) * Math.cos((lat1Rad + lat2Rad) / 2);
        double y = (lat2Rad - lat1Rad);

        return Math.sqrt(x * x + y * y) * EARTH_RADIUS;
    }

    public Currency getCurrencyByCountryCode(String countryCode) {
        return Currency.getInstance(Locale.of("", countryCode));
    }

    public Set<String> getCurrentTimesByCountry(String countryCode, String countryName) {
        var now = Instant.now();
        var locale = Locale.of("", countryCode);

        var zones = ZoneId.getAvailableZoneIds().stream()
                .filter(zoneId -> zoneId.startsWith(locale.getCountry()))
                .toList();

        if(zones.isEmpty()) {
            zones = ZoneId.getAvailableZoneIds().stream()
                    .filter(zoneId -> zoneId.contains(countryName))
                    .toList();

            if(zones.isEmpty()) {
                return null;
            }
        }

        return zones.stream()
                .map(zoneId -> ZonedDateTime.ofInstant(now, ZoneId.of(zoneId)).format(ISO_OFFSET_DATE_TIME))
                .collect(Collectors.toSet());
    }

    public BigDecimal getDollarExchangeRate(ExchangeRatesDto dto, String currencyCode) {
        var rates = dto.getRates();
        if(rates == null || rates.isEmpty() || !rates.containsKey(USD_CODE) || !rates.containsKey(currencyCode)) {
            return null;
        }

        var eurToUsdRate = rates.get(USD_CODE);
        var currencyToEur = rates.get(currencyCode);
        return eurToUsdRate.divide(currencyToEur, 6, UP);
    }
}
