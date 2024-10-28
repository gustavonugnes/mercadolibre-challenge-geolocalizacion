package com.gnugnes.mercadolibre_challenge_geolocalizacion.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gnugnes.mercadolibre_challenge_geolocalizacion.dtos.CountryDto;
import com.gnugnes.mercadolibre_challenge_geolocalizacion.services.IpService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(IpController.class)
class IpControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IpService ipService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getIpData_shouldReturnCountryDto() throws Exception {
        var ip = "8.8.8.8";

        var countryDto = new CountryDto();
        countryDto.setName("Argentina");

        when(ipService.getCountryData(ip)).thenReturn(countryDto);

        mockMvc.perform(get("/ips/{ip}", ip))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json; charset=utf-8"))
                .andExpect(content().json(objectMapper.writeValueAsString(countryDto)));

        verify(ipService).getCountryData("8.8.8.8");
    }

    @Test
    void getIpData_shouldReturnCountryDto_invalidFormat() throws Exception {
        var ip = "1234";

        mockMvc.perform(get("/ips/{ip}", ip))
                .andExpect(status().isBadRequest());

        verify(ipService, never()).getCountryData(anyString());
    }
}