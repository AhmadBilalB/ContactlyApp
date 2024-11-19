package com.example.contactly.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Contactly API")
                        .version("1.0.0")
                        .description("API documentation for the Contactly application.")
                        .termsOfService("https://example.com/terms")
                        .contact(new Contact()
                                .name("Support Team")
                                .email("support@contactly.com")
                                .url("https://contactly.com"))
                        );
    }
}

