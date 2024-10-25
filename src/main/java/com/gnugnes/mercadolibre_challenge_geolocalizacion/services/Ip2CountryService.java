package com.gnugnes.mercadolibre_challenge_geolocalizacion.services;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gnugnes.mercadolibre_challenge_geolocalizacion.dtos.Ip2CountryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class Ip2CountryService {

    private final ObjectMapper objectMapper;

    public Ip2CountryDto getCountryDataFake(String ip) {
        TypeReference<Ip2CountryDto> typeReference = new TypeReference<>() {
        };
        InputStream inputStream = TypeReference.class.getResourceAsStream("/json/country(es).json");
        try {
            Ip2CountryDto dto = objectMapper.readValue(inputStream, typeReference);

            return dto;
        } catch (IOException e) {
            System.out.println("Unable to save users: " + e.getMessage());
        }
        return null;
    }
}
