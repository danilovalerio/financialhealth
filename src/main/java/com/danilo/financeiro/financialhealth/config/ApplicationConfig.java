package com.danilo.financeiro.financialhealth.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Arquivo para configurações globais da aplicação
 */
@Configuration
public class ApplicationConfig {

    /**
     * Disponibiliza o ModelMapper para todos
     * @return
     */
    @Bean
    public ModelMapper mapper(){
        return new ModelMapper();
    }
}
