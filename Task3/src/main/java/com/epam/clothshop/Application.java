package com.epam.clothshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@SpringBootApplication
@EnableSwagger2
@EnableTransactionManagement
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, jsr250Enabled = true)
public class Application implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.epam.clothshop.api"))
                .paths(PathSelectors.ant("/api/**"))
                .build()
                .apiInfo(new ApiInfo(
                        "Cloth Shop",
                        "This is a sample API for Spring Boot application for EPAM's Internal Lab program",
                        "1.0.0",
                        null,
                        null,
                        null,
                        null,
                        Collections.emptyList()
                ));
    }
}
