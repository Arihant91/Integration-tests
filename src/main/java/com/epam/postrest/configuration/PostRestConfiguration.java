package com.epam.postrest.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.epam.postrest.converter.JavaScriptMessageConverter;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class PostRestConfiguration {

    @Bean
    public RestTemplate restTemplate(JavaScriptMessageConverter javaScriptMessageConverter) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(javaScriptMessageConverter);
        return new RestTemplate();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}
