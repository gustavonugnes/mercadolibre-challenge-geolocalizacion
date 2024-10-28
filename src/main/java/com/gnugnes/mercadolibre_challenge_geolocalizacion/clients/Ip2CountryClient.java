package com.gnugnes.mercadolibre_challenge_geolocalizacion.clients;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gnugnes.mercadolibre_challenge_geolocalizacion.dtos.Ip2CountryDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@Component
public class Ip2CountryClient {

    private static final String BASE_URL = "http://api.ipapi.com";

    /**
     * Hardcoded access key for external service. This is only used for testing purposes
     * and not intended for production use. In a real application, the access key should
     * be retrieved from a secure source, such as environment variables, a secure vault,
     * or a configuration management system, to prevent exposure and enhance security.
     */
    private static final String ACCESS_KEY = "20580b936a8be84b532774cf2f119ea6";

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public Ip2CountryClient(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;

        this.webClient = WebClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .build();
    }

    public Ip2CountryDto getCountryData(String ip) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(ip)
                        .queryParam("access_key", ACCESS_KEY)
                        .queryParam("language", "es")
                        .build())
                .retrieve()
                .bodyToMono(Ip2CountryDto.class)
                .block();
    }

    /**
     * Mock method to simulate retrieving country data based on an IP address, bypassing an actual call
     * to the external IP API (https://ipapi.com/). This method is used to avoid consuming limited
     * API requests during testing or development. Instead, it reads from a local JSON file
     * (`ip2country_3.json`) that contains sample data formatted as an Ip2CountryDto.
     */
    public Ip2CountryDto getCountryDataMock(String ip) {
        TypeReference<Ip2CountryDto> typeReference = new TypeReference<>() {
        };
        var inputStream = TypeReference.class.getResourceAsStream("/json/mocks/ip2country_3.json");
        try {
            return objectMapper.readValue(inputStream, typeReference);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
