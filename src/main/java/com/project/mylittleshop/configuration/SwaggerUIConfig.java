package com.project.mylittleshop.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerUIConfig {
    
    @Bean
    public OpenAPI configOpenApi(){
        final String cookieSchemeName = "cookieAuth";
        return new OpenAPI()
                .info(new Info()
                        .title("Microservice: My little Shop API")
                        .version("1.0.0")
                )
                .addSecurityItem(new SecurityRequirement().addList(cookieSchemeName))
                .components(new Components()
                        .addSecuritySchemes(cookieSchemeName,new SecurityScheme()
                                .name("jwt")
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.COOKIE)));
    }
}
