package com.smdev.smsj.security.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author sm, in 2018
 *
 * |> ModelMapperConfig ~~ [com.smdev.smsj.security.config]
 * 
 */
@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
