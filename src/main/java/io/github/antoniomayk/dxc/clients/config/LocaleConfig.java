package io.github.antoniomayk.dxc.clients.config;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.LocaleResolver;

/**
 * Configuration for the locale resolution mechanism in the application. It provides a custom
 * implementation of the {@link LocaleResolver} interface, which determines the user's locale based
 * on the "Accept-Language" header sent in the HTTP request.
 *
 * @author Antonio Mayk
 * @since 0.1
 */
@Configuration
public class LocaleConfig {
  private static final List<Locale> LOCALES = Arrays.asList(new Locale("en"), new Locale("pt"));

  @Bean
  LocaleResolver localeResolver() {
    return new LocaleResolver() {
      @Override
      public @NonNull Locale resolveLocale(@NonNull HttpServletRequest request) {
        final var acceptLanguage = request.getHeader("Accept-Language");
        return (acceptLanguage != null && !acceptLanguage.isEmpty())
            ? Locale.lookup(Locale.LanguageRange.parse(acceptLanguage), LOCALES)
            : Locale.getDefault();
      }

      @Override
      public void setLocale(
          @NonNull HttpServletRequest arg0,
          @Nullable HttpServletResponse arg1,
          @Nullable Locale arg2) {
        // Nothing to do here...
      }
    };
  }
}
