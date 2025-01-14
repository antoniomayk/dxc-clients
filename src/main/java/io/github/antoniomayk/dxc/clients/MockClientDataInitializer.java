package io.github.antoniomayk.dxc.clients;

import com.github.javafaker.Faker;
import java.util.ArrayList;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Initializes the database with mock client data on application startup.
 *
 * @author Antonio Mayk
 * @since 0.1
 */
@Component
@Profile("!test")
public class MockClientDataInitializer implements CommandLineRunner {
  private static final Logger log = LoggerFactory.getLogger(App.class);

  private static final Random random = new Random();

  final JdbcTemplate jdbcTemplate;

  MockClientDataInitializer(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public void run(String... strings) throws Exception {
    final var values = new ArrayList<Object[]>();
    final var count = random.nextInt(1000);

    log.info("Generating {} mock clients", count);

    for (int i = 0; i < count; i++) {
      final var faker = new Faker();
      final var triplet =
          new Object[] {
            faker.name().fullName(),
            faker.internet().emailAddress(),
            faker.phoneNumber().cellPhone()
          };
      values.add(triplet);
    }

    jdbcTemplate.batchUpdate(
        "INSERT INTO clients (full_name, email, phone_number) VALUES (?, ?, ?)", values);

    log.info("Inserted {} mock clients into the 'clients' table", values.size());
  }
}
