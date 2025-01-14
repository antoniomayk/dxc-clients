package io.github.antoniomayk.dxc.clients.config;

import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Implements a {@link AuditorAware} to set the current user as the auditor for entities that
 * support auditing. If no user is authenticated, the auditor will be set to "SPRING_API".
 *
 * @author Antonio Mayk
 * @since 0.1
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class AuditConfig {
  @Bean
  AuditorAware<String> auditorProvider() {
    return () -> {
      final var authentication = SecurityContextHolder.getContext().getAuthentication();

      if (authentication == null) {
        return Optional.of("SPRING_API");
      }

      return Optional.ofNullable(authentication.getName());
    };
  }
}
