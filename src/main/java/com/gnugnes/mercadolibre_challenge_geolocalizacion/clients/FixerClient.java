package com.gnugnes.mercadolibre_challenge_geolocalizacion.clients;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gnugnes.mercadolibre_challenge_geolocalizacion.dtos.ExchangeRatesDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@Component
public class FixerClient {

    private static final String BASE_URL = "http://data.fixer.io/api";

    /**
     * Hardcoded access key for external service. This is only used for testing purposes
     * and not intended for production use. In a real application, the access key should
     * be retrieved from a secure source, such as environment variables, a secure vault,
     * or a configuration management system, to prevent exposure and enhance security.
     */
    private static final String ACCESS_KEY = "f76e73194322bee88b5cd3e760727ffd";

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public FixerClient(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;

        this.webClient = WebClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .build();
    }

    public ExchangeRatesDto getExchangeRates() {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/latest")
                        .queryParam("access_key", ACCESS_KEY)
                        .build())
                .retrieve()
                .bodyToMono(ExchangeRatesDto.class)
                .block();
    }

    /**
     * Mock method to simulate the retrieval of exchange rates data, bypassing an actual call
     * to the external Fixer API (https://fixer.io/). This is used to avoid consuming limited
     * API requests during testing or development. Instead, it reads from a local JSON file
     * (`fixer_exchange_rates.json`) that contains sample data formatted as an ExchangeRatesDto.
     */
    public ExchangeRatesDto getExchangeRatesMock() {
        TypeReference<ExchangeRatesDto> typeReference = new TypeReference<>() {
        };
        var inputStream = TypeReference.class
                .getResourceAsStream("/json/mocks/fixer_exchange_rates.json");
        try {
            return objectMapper.readValue(inputStream, typeReference);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
