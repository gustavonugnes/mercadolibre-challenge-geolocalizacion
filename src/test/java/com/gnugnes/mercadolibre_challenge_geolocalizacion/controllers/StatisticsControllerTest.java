package com.gnugnes.mercadolibre_challenge_geolocalizacion.controllers;

import com.gnugnes.mercadolibre_challenge_geolocalizacion.services.StatisticsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StatisticsController.class)
class StatisticsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StatisticsService statisticsService;

    @Test
    void getMaxDistance_shouldReturnMaxDistance() throws Exception {
        var maxDistance = 1000.0;

        when(statisticsService.getMaxDistance()).thenReturn(maxDistance);

        mockMvc.perform(get("/statistics/max"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json; charset=utf-8"))
                .andExpect(content().json(String.valueOf(maxDistance)));
    }

    @Test
    void getMinDistance_shouldReturnMinDistance() throws Exception {
        var minDistance = 100.0;

        when(statisticsService.getMinDistance()).thenReturn(minDistance);

        mockMvc.perform(get("/statistics/min"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json; charset=utf-8"))
                .andExpect(content().json(String.valueOf(minDistance)));
    }

    @Test
    void getAverageDistance_shouldReturnAverageDistance() throws Exception {
        var averageDistance = 500.0;

        when(statisticsService.getAverageDistance()).thenReturn(averageDistance);

        mockMvc.perform(get("/statistics/average"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json; charset=utf-8"))
                .andExpect(content().json(String.valueOf(averageDistance)));
    }
}
