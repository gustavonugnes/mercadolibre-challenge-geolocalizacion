package com.gnugnes.mercadolibre_challenge_geolocalizacion.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gnugnes.mercadolibre_challenge_geolocalizacion.dtos.Ip2CountryDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Service
//@RequiredArgsConstructor
public class Ip2CountryService {

    private static final String BASE_URL = "http://api.ipapi.com";
    private static final String ACCESS_KEY = "20580b936a8be84b532774cf2f119ea6";

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public Ip2CountryService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;

        this.webClient = WebClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public Ip2CountryDto getCountryDataFake(String ip) {
        TypeReference<Ip2CountryDto> typeReference = new TypeReference<>() {
        };
        InputStream inputStream = TypeReference.class.getResourceAsStream("/json/country_ar.json");
        try {
            return objectMapper.readValue(inputStream, typeReference);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return null;
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

}
