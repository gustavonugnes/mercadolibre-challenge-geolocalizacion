package com.gnugnes.mercadolibre_challenge_geolocalizacion.services;

import com.gnugnes.mercadolibre_challenge_geolocalizacion.dtos.CountryDto;
import com.gnugnes.mercadolibre_challenge_geolocalizacion.dtos.LanguageDto;
import com.gnugnes.mercadolibre_challenge_geolocalizacion.entities.Invocation;
import com.gnugnes.mercadolibre_challenge_geolocalizacion.external.FixerClient;
import com.gnugnes.mercadolibre_challenge_geolocalizacion.external.Ip2CountryClient;
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

    public CountryDto getCountryData(String ip) {
//        var data = ip2CountryClient.getCountryDataFake(ip);
        var data = ip2CountryClient.getCountryData(ip);

        var countryDto = new CountryDto();
        countryDto.setIp(data.getIp());
        countryDto.setLocalDateTime(LocalDateTime.now());
        countryDto.setName(data.getCountryName());
        countryDto.setIsoCode(data.getCountryCode());

        if (data.getLocation() != null && data.getLocation().getLanguages() != null) {
            countryDto.setLanguages(
                    data.getLocation().getLanguages().stream().map(each -> {
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
        var currency = utilsService.getCurrencyByCountryCode(data.getCountryCode());

        countryDto.setCurrencyCode(currency.getCurrencyCode());
        countryDto.setCurrencyName(currency.getDisplayName());

        /* The data I can get via the "Fixer" Service with a free plan just allows to get
         exchanges rates based on the Euro. In order to change it a would need a paid plan.
         So, as a workaround I get the EUR/USD and {currentCurrency}/EUR and then divide them
         to get the {currentCurrency}/USD that is needed.
        */
        countryDto.setCurrencyExchangeRateWithUsDollar(
                utilsService.getDollarExchangeRate(fixerClient.getExchangeRates(),
                        currency.getCurrencyCode()));
//        countryDto.setCurrencyExchangeRateWithUsDollar(Utils.getDollarExchangeRate(fixerClient.getExchangeRatesFake(), currency.getCurrencyCode()));


        /* Similar to what was mentioned above.
        * I am not able to get the timezone data neither from the  "IP2Country (IpApi)" service nor from
        * the "Country Layer" Service using a free plan.
        * As a workaround for that issue I try to find the timezones vía the country's name and code name.
        * See details in the Utils class.
        * Please keep in mind This approach will not work for all cases.
        * */
        countryDto.setTimeZones(utilsService.getCurrentTimesByCountry(data.getCountryCode(), data.getCountryName()));

        var distanceFromBuenosAires = utilsService.getDistanceFromBuenosAires(
                data.getLatitude(), data.getLongitude());

        countryDto.setLatitude(data.getLatitude());
        countryDto.setLongitude(data.getLongitude());
        countryDto.setBuenosAiresLatitude(BUENOS_AIRES_LAT);
        countryDto.setBuenosAiresLongitude(BUENOS_AIRES_LON);
        countryDto.setDistanceToBuenosAires(distanceFromBuenosAires);

        var invocation = invocationRepository.findByCountryCode(data.getCountryCode());
        if (invocation == null) {
            invocation = new Invocation();
            invocation.setAmount(0L);
        }

        invocation.setCountryCode(data.getCountryCode());
        invocation.setDistance(distanceFromBuenosAires);
        invocation.setAmount(invocation.getAmount() + 1);

        invocationRepository.save(invocation);

        return countryDto;
    }
}
