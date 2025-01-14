package io.github.antoniomayk.dxc.clients.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Security settings for the application.
 *
 * <p>Custom configurations:
 *
 * <ul>
 *   <li>Cross-Site Request Forgery (CSRF) protection is disabled.
 *   <li>All requests are required to be authenticated.
 *   <li>HTTP Basic authentication is enabled.
 * </ul>
 *
 * @author Antonio Mayk
 * @since 0.1
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable())
        .authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
        .httpBasic(withDefaults());
  }
}
