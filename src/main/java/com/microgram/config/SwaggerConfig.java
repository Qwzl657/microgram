package com.microgram.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI microgramOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Microgram API")
                        .description("REST API для Instagram-подобного приложения")
                        .version("1.0.0"));
    }
}