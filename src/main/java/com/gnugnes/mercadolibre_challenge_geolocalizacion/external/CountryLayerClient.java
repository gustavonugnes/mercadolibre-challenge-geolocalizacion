package com.gnugnes.mercadolibre_challenge_geolocalizacion.external;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class CountryLayerClient {

    private static final String BASE_URL = "https://api.countrylayer.com/v2/";
    private static final String ACCESS_KEY = "275c470debcd601e042f4f8e9a0c7ffc";

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public CountryLayerClient(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;

        this.webClient = WebClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public void getCountryData() {

    }

}
