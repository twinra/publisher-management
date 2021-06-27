package com.github.twinra.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;
import java.util.function.Predicate;

@Configuration
@EnableWebMvc
public class SwaggerConfig {

    //TODO: move to application.yaml as properties
    public static final String TITLE = "Publishers and Books Manager";
    public static final String CONTACT_NAME = "Team";
    public static final String CONTACT_URL = "https://github.com/twinra";
    public static final String CONTACT_EMAIL = "email@domain.com";
    public static final List<String> PATHS = List.of("/publishers.*", "/books.*");

    @Bean
    public WebMvcConfigurer swaggerMvConfig() {
        return new WebMvcConfigurer() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addRedirectViewController("/", "swagger-ui/index.html");
            }
        };
    }

    @Bean
    public Docket swaggerDocConfig() {

        Predicate<String> pathPredicates = PATHS.stream()
                .map(PathSelectors::regex)
                .reduce(Predicate::or)
                .orElse(x -> false);

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(pathPredicates)
                .build()
                .pathMapping("/")
                .useDefaultResponseMessages(false)
                .apiInfo(new ApiInfoBuilder().title(TITLE).contact(
                        new Contact(CONTACT_NAME, CONTACT_URL, CONTACT_EMAIL)
                ).build());
    }
}
