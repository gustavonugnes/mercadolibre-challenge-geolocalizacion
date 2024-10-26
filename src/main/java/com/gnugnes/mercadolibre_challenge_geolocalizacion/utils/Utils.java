package com.gnugnes.mercadolibre_challenge_geolocalizacion;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class Utils {

    private static final double EARTH_RADIUS = 6371;

    private static final double BUENOS_AIRES_LAT = -34.61315;
    private static final double BUENOS_AIRES_LON = -58.37723;

    private Utils() {

    }

    public static double distanceFromBuenosAires(double lat1, double lon1) {
        return calculateDistance(BUENOS_AIRES_LAT, BUENOS_AIRES_LON, lat1, lon1);
    }

    // Uses the Equirectangular Distance Approximation
    // Not extremely accurate but very fast, so it should be enough for the purposes of this application
    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double lat1Rad = Math.toRadians(lat1);
        double lat2Rad = Math.toRadians(lat2);
        double lon1Rad = Math.toRadians(lon1);
        double lon2Rad = Math.toRadians(lon2);

        double x = (lon2Rad - lon1Rad) * Math.cos((lat1Rad + lat2Rad) / 2);
        double y = (lat2Rad - lat1Rad);

        return Math.sqrt(x * x + y * y) * EARTH_RADIUS;
    }

    public static Currency getCurrencyByCountryCode(String countryCode) {
        var locale = Locale.of("", countryCode);
        return Currency.getInstance(locale);
    }

    public static List<String> getCurrentTimesByCountry(String countryCode) {
        var now = Instant.now();

        var locale = Locale.of("", countryCode);

        // Formatter for UTC format (e.g., "20:01:23 (UTC)")
        var utcFormatter = DateTimeFormatter.ofPattern("HH:mm:ss '(UTC)'");

        // Formatter for UTC with offset (e.g., "21:01:23 (UTC+01:00)")
        var offsetFormatter = DateTimeFormatter.ofPattern("HH:mm:ss '(UTC'xxx')'");

        return ZoneId.getAvailableZoneIds().stream()
                .filter(zoneId -> zoneId.startsWith(locale.getCountry()))
                .map(zoneId -> {
                    var zonedDateTime = ZonedDateTime.ofInstant(now, ZoneId.of(zoneId));
                    var utcTime = zonedDateTime.withZoneSameInstant(ZoneId.of("UTC")).format(utcFormatter);
                    var localTime = zonedDateTime.format(offsetFormatter);
                    return utcTime + " or " + localTime;
                }).toList();
    }
}
