package com.merck.library_management_system.configuration;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.service.contexts.SecurityContext;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.merck"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiDetails())
                .securitySchemes(Collections.singletonList(apiKey())) // Include the API key for security
                .securityContexts(Collections.singletonList(securityContext())); // Apply the security context to all paths
    }

    private ApiInfo apiDetails() {
        return new ApiInfo(
                "Library Management System",
                "API documentation for Library Management System",
                "1.0",
                "Free to use",
                new springfox.documentation.service.Contact("Devesh Yadav", "dev-tech.com", "library-management-system@dev-tech.com"),
                "API License",
                "your-license-url",
                Collections.emptyList());
    }

    private ApiKey apiKey() {
        // Define the API Key for bearer authentication
        return new ApiKey("bearerAuth", "Authorization", "header");
    }

    private SecurityContext securityContext() {
        // Configure security context to require bearer authentication
        return SecurityContext.builder()
                .securityReferences(defaultAuth()) // Reference the default authentication
                .forPaths(PathSelectors.any()) // Apply to all paths
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        // Define the authorization scope for the bearer token
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        return Collections.singletonList(new SecurityReference("bearerAuth", new AuthorizationScope[]{authorizationScope}));
    }
}
