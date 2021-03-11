package com.augustomesquita.billsapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 * @author Augusto
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    
    @Value("${spring.profiles.active}")
    private String environment;
    
    @Bean
    public Docket api() {
        boolean enableSwagger = environment.equalsIgnoreCase("dev-local");
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.augustomesquita.billsapp.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .tags(new Tag("Bill", "Endpoints for creating and retrieving bills."))
                .enable(enableSwagger)
                .forCodeGeneration(true);
    }
    
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .description("This app was made by Augusto Mesquita for an interview and is responsible to manage bills.")
                .title("Bills App RESTful API")
                .version("1.0.0")
                .build();
    }
}
