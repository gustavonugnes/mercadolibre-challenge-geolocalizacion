package com.gnugnes.mercadolibre_challenge_geolocalizacion.services;

import com.gnugnes.mercadolibre_challenge_geolocalizacion.clients.FixerClient;
import com.gnugnes.mercadolibre_challenge_geolocalizacion.clients.Ip2CountryClient;
import com.gnugnes.mercadolibre_challenge_geolocalizacion.config.MockConfig;
import com.gnugnes.mercadolibre_challenge_geolocalizacion.dtos.CountryDto;
import com.gnugnes.mercadolibre_challenge_geolocalizacion.dtos.Ip2CountryDto;
import com.gnugnes.mercadolibre_challenge_geolocalizacion.dtos.LanguageDto;
import com.gnugnes.mercadolibre_challenge_geolocalizacion.entities.Invocation;
import com.gnugnes.mercadolibre_challenge_geolocalizacion.repositories.InvocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.gnugnes.mercadolibre_challenge_geolocalizacion.services.UtilsService.BUENOS_AIRES_LAT;
import static com.gnugnes.mercadolibre_challenge_geolocalizacion.services.UtilsService.BUENOS_AIRES_LON;

@Service
@RequiredArgsConstructor
public class IpService {

    private final Ip2CountryClient ip2CountryClient;
    private final FixerClient fixerClient;
    private final InvocationRepository invocationRepository;
    private final UtilsService utilsService;
    private final MockConfig mockConfig;

    public CountryDto getCountryData(String ip) {
        Ip2CountryDto ip2CountryDto;
        if(mockConfig.isEnabled()) {
            ip2CountryDto = ip2CountryClient.getCountryDataMock(ip);
        } else {
            ip2CountryDto = ip2CountryClient.getCountryData(ip);
        }

        var countryDto = new CountryDto();
        countryDto.setIp(ip2CountryDto.getIp());
        countryDto.setLocalDateTime(LocalDateTime.now());
        countryDto.setName(ip2CountryDto.getCountryName());
        countryDto.setIsoCode(ip2CountryDto.getCountryCode());

        if (ip2CountryDto.getLocation() != null && ip2CountryDto.getLocation().getLanguages() != null) {
            countryDto.setLanguages(ip2CountryDto.getLocation().getLanguages().stream().map(each -> {
                var languageDto = new LanguageDto();
                languageDto.setName(each.getName());
                languageDto.setCode(each.getCode());
                languageDto.setNativeName(each.getNativeName());
                return languageDto;
            }).toList());
        }

        /* Originally I wanted to get the currency of the country via the "IP2Country (IpApi)" service.
        However, the free plan I have does not return the currency data. (Requires a paid plan)
        So I implemented a workaround using just Java code, as you can see in the Utils class.
        */
        var currency = utilsService.getCurrencyByCountryCode(ip2CountryDto.getCountryCode());

        countryDto.setCurrencyCode(currency.getCurrencyCode());
        countryDto.setCurrencyName(currency.getDisplayName());

        /* The data I can get via the "Fixer" Service with a free plan just allows to get
         exchanges rates based on the Euro. In order to change it a would need a paid plan.
         So, as a workaround I get the EUR/USD and {currentCurrency}/EUR and then divide them
         to get the {currentCurrency}/USD that is needed.
        */

        if(mockConfig.isEnabled()) {
            countryDto.setCurrencyExchangeRateWithUsDollar(utilsService.getDollarExchangeRate(
                fixerClient.getExchangeRatesMock(), currency.getCurrencyCode()));
        } else {
            countryDto.setCurrencyExchangeRateWithUsDollar(
                    utilsService.getDollarExchangeRate(fixerClient.getExchangeRates(),
                            currency.getCurrencyCode()));
        }

        /* Similar to what was mentioned above.
         * I am not able to get the timezone data neither from the  "IP2Country (IpApi)" service nor from
         * the "Country Layer" Service using a free plan.
         * As a workaround for that issue I try to find the timezones vía the country's name and code name.
         * See details in the Utils class.
         * Please keep in mind This approach will not work for all cases.
         * */
        countryDto.setTimeZones(utilsService.getCurrentTimesByCountry(ip2CountryDto.getCountryCode(),
                ip2CountryDto.getCountryName()));

        var distanceFromBuenosAires = utilsService.getDistanceFromBuenosAires(ip2CountryDto.getLatitude(),
                ip2CountryDto.getLongitude());

        countryDto.setLatitude(ip2CountryDto.getLatitude());
        countryDto.setLongitude(ip2CountryDto.getLongitude());
        countryDto.setBuenosAiresLatitude(BUENOS_AIRES_LAT);
        countryDto.setBuenosAiresLongitude(BUENOS_AIRES_LON);
        countryDto.setDistanceToBuenosAires(distanceFromBuenosAires);

        var invocation = new Invocation();
        invocation.setCountryCode(ip2CountryDto.getCountryCode());
        invocation.setDistance(distanceFromBuenosAires);

        invocationRepository.save(invocation);

        return countryDto;
    }
}
