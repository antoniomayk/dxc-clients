package io.github.antoniomayk.dxc.clients.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * Manage the {@link MessageSource} and {@link LocalValidatorFactoryBean} beans. Used to provide
 * internationalization (i18n) support for messages in the application.
 *
 * @author Antonio Mayk
 * @since 0.1
 */
@Configuration
public class MessageSourceConfig {
  @Bean
  LocalValidatorFactoryBean localValidatorFactoryBean(MessageSource messageSource) {
    LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
    bean.setValidationMessageSource(messageSource);
    return bean;
  }
}
