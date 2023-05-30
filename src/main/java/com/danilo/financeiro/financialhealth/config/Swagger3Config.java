package com.danilo.financeiro.financialhealth.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Swagger3Config {

    @Bean
    public GroupedOpenApi publicApi() {
        GroupedOpenApi groupedOpenApi = GroupedOpenApi.builder()
                .group("financialhealth-public")
                .pathsToMatch(
                        "/api/dashboard/**",
                        "/api/centrosdecusto/**",
                        "/api/usuarios/**"
                )
                .build();

        return groupedOpenApi;
    }
}
