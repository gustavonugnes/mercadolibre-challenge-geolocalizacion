package com.gnugnes.mercadolibre_challenge_geolocalizacion.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "services.mock")
@Getter
@Setter
public class MockConfig {

    private boolean enabled;
}
