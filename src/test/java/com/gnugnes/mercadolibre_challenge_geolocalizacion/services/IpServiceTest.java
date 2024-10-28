package com.gnugnes.mercadolibre_challenge_geolocalizacion.services;

import com.gnugnes.mercadolibre_challenge_geolocalizacion.clients.FixerClient;
import com.gnugnes.mercadolibre_challenge_geolocalizacion.clients.Ip2CountryClient;
import com.gnugnes.mercadolibre_challenge_geolocalizacion.config.MockConfig;
import com.gnugnes.mercadolibre_challenge_geolocalizacion.dtos.ExchangeRatesDto;
import com.gnugnes.mercadolibre_challenge_geolocalizacion.dtos.Ip2CountryDto;
import com.gnugnes.mercadolibre_challenge_geolocalizacion.dtos.LanguageDto;
import com.gnugnes.mercadolibre_challenge_geolocalizacion.entities.Invocation;
import com.gnugnes.mercadolibre_challenge_geolocalizacion.repositories.InvocationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IpServiceTest {

    @InjectMocks
    private IpService ipService;

    @Mock
    private Ip2CountryClient ip2CountryClient;

    @Mock
    private FixerClient fixerClient;

    @Mock
    private InvocationRepository invocationRepository;

    @Mock
    private UtilsService utilsService;

    @Mock
    private MockConfig mockConfig;

    @Captor
    private ArgumentCaptor<Invocation> invocationArgumentCaptor;

    private final ExchangeRatesDto exchangeRatesDto = new ExchangeRatesDto();

    @BeforeEach
    void setUp() {
        var ip2CountryDto = new Ip2CountryDto();
        ip2CountryDto.setIp("134.201.250.152");
        ip2CountryDto.setCountryName("Argentina");
        ip2CountryDto.setCountryCode("AR");
        ip2CountryDto.setLatitude(36.174);
        ip2CountryDto.setLongitude(-115.318);

        var languageDto = new Ip2CountryDto.LocationDto.LanguageDto();
        languageDto.setCode("es");
        languageDto.setName("Spanish");
        languageDto.setNativeName("Español");

        var location = new Ip2CountryDto.LocationDto();
        location.setLanguages(List.of(languageDto));

        ip2CountryDto.setLocation(location);

        var currency = Currency.getInstance(Locale.of("es", "AR"));

        when(mockConfig.isEnabled()).thenReturn(false);
        when(ip2CountryClient.getCountryData("134.201.250.152")).thenReturn(ip2CountryDto);
        when(utilsService.getCurrencyByCountryCode("AR")).thenReturn(currency);
        when(utilsService.getCurrentTimesByCountry("AR", "Argentina")).
                thenReturn(Set.of("2024-10-27T15:43:32-03:00"));
        when(utilsService.getDistanceFromBuenosAires(any(), any())).thenReturn(12.36);
        when(fixerClient.getExchangeRates()).thenReturn(exchangeRatesDto);
        when(utilsService.getDollarExchangeRate(exchangeRatesDto, "ARS")).
                thenReturn(new BigDecimal("0.001"));
    }

    @Test
    void testGetCountryData() {
        var ip = "134.201.250.152";
        var result = ipService.getCountryData(ip);

        assertThat(result.getIp()).isEqualTo("134.201.250.152");
        assertThat(result.getLocalDateTime()).isNotNull();
        assertThat(result.getName()).isEqualTo("Argentina");
        assertThat(result.getIsoCode()).isEqualTo("AR");
        assertThat(result.getLanguages()).isNotEmpty();
        assertThat(result.getLanguages()).extracting(LanguageDto::getCode).containsExactly("es");
        assertThat(result.getLanguages()).extracting(LanguageDto::getName).containsExactly("Spanish");
        assertThat(result.getLanguages()).extracting(LanguageDto::getNativeName).containsExactly("Español");
        assertThat(result.getCurrencyCode()).isEqualTo("ARS");
        assertThat(result.getCurrencyName()).isEqualTo("peso argentino");
        assertThat(result.getCurrencyExchangeRateWithUsDollar()).isEqualTo("0.001");
        assertThat(result.getTimeZones()).isNotEmpty();
        assertThat(result.getTimeZones()).containsExactly("2024-10-27T15:43:32-03:00");
        assertThat(result.getLatitude()).isEqualTo(36.174);
        assertThat(result.getLongitude()).isEqualTo(-115.318);
        assertThat(result.getBuenosAiresLatitude()).isEqualTo(-34.61315);
        assertThat(result.getBuenosAiresLongitude()).isEqualTo(-58.37723);
        assertThat(result.getDistanceToBuenosAires()).isEqualTo(12.36);

        verify(ip2CountryClient).getCountryData("134.201.250.152");
        verify(utilsService).getCurrencyByCountryCode("AR");
        verify(utilsService).getCurrentTimesByCountry("AR", "Argentina");
        verify(utilsService).getDistanceFromBuenosAires(36.174, -115.318);
        verify(fixerClient).getExchangeRates();
        verify(utilsService).getDollarExchangeRate(exchangeRatesDto, "ARS");
        verify(invocationRepository).save(invocationArgumentCaptor.capture());
        var insertedInvocation = invocationArgumentCaptor.getValue();
        assertThat(insertedInvocation.getCountryCode()).isEqualTo("AR");
        assertThat(insertedInvocation.getDistance()).isEqualTo(12.36);
    }
}
