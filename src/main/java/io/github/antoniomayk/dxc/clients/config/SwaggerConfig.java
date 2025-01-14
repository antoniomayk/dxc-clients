package io.github.antoniomayk.dxc.clients.config;

import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Configuration class for Swagger API documentation.
 *
 * @author Antonio Mayk
 * @since 0.1
 */
@Configuration
public class SwaggerConfig {

  @Bean
  Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("io.github.antoniomayk.dxc.clients"))
        .paths(PathSelectors.any())
        .build()
        .globalRequestParameters(
            Collections.singletonList(
                new RequestParameterBuilder()
                    .name("Accept-Language")
                    .description("Examples: en-US, pt-BR;0.9")
                    .in("header")
                    .required(false)
                    .build()));
  }
}
