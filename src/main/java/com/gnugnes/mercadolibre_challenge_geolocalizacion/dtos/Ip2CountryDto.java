package com.gnugnes.mercadolibre_challenge_geolocalizacion.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Ip2CountryDto {

    private String ip;
    private String type;
    private String continentCode;
    private String continentName;
    private String countryCode;
    private String countryName;
    private String regionCode;
    private String regionName;
    private String city;
    private String zip;
    private Double latitude;
    private Double longitude;
    private String msa;
    private String dma;
    private String radius;
    private String ipRoutingType;
    private String connectionType;
    private LocationDto location;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LocationDto {
        private Integer geonameId;
        private String capital;
        private List<LanguageDto> languages;
        private String countryFlag;
        private String countryFlagEmoji;
        private String countryFlagEmojiUnicode;
        private String callingCode;
        private Boolean isEu;

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class LanguageDto {
            private String code;
            private String name;

            @JsonProperty("native")
            private String nativeName; // Changed 'native' to 'nativeName' to avoid conflict with Java keyword
        }
    }

}
