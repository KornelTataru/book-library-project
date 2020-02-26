package com.booklibrary.libraryservice.swagger

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CustomOpenApiConf {
    @Bean
    fun customApiConf(): OpenAPI = OpenAPI()
            .info(Info().title("Book Library API").version("0").description("Practice Project"))
}