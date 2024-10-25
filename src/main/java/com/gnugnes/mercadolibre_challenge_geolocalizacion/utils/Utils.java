package com.gnugnes.mercadolibre_challenge_geolocalizacion;

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
}
