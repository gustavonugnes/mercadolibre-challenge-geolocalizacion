package com.gnugnes.mercadolibre_challenge_geolocalizacion.external;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gnugnes.mercadolibre_challenge_geolocalizacion.dtos.ExchangeRatesDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Component
public class FixerClient {

    private static final String BASE_URL = "http://data.fixer.io/api";
    private static final String ACCESS_KEY = "f76e73194322bee88b5cd3e760727ffd";

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public FixerClient(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;

        this.webClient = WebClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public ExchangeRatesDto getExchangeRatesFake() {
        TypeReference<ExchangeRatesDto> typeReference = new TypeReference<>() {
        };
        InputStream inputStream = TypeReference.class.getResourceAsStream("/json/exchange_rates.json");
        try {
            return objectMapper.readValue(inputStream, typeReference);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
