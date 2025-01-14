package io.github.antoniomayk.dxc.clients;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * The main entry point for the Spring Boot application.
 *
 * @author Antonio Mayk
 * @since 0.1
 */
@SpringBootApplication
@EnableSwagger2
public class App {
  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }
}
