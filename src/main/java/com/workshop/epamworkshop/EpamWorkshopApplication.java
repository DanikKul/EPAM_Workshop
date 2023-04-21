package com.workshop.epamworkshop;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableCaching
@EnableJpaRepositories
@EntityScan("com.workshop.epamworkshop.model")
@ComponentScan("com.workshop.epamworkshop.*")
@Configuration
public class EpamWorkshopApplication {

    public static void main(String[] args) {
        SpringApplication.run(EpamWorkshopApplication.class, args);
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info().title("EPAM Workshops API")
                        .description("Simple REST Web service")
                        .version("v0.0.1")
                        .contact(new Contact().name("Daniil Kulakovich")
                        .email("kulakovich2@gmail.com")
                        .url(""))
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Workshop Wiki Documentation")
                        .url(""));


    }

}
