package io.github.antoniomayk.dxc.clients.constraint;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import io.github.antoniomayk.dxc.clients.validator.PhoneNumberValidator;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * The annotated element must not be {@code null} and must be a valid Phone number. Accepts {@link
 * CharSequence}.
 *
 * @see PhoneNumberValidator
 * @author Antonio Mayk
 * @since 0.1
 */
@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = PhoneNumberValidator.class)
public @interface PhoneNumber {

  /**
   * Returns the error message template when the phone number validation fails.
   *
   * @return the error message template
   */
  String message() default "{io.github.antoniomayk.dxc.clients.constraint.PhoneNumber}";

  /**
   * Returns the groups with which the constraint is associated.
   *
   * @return an array of group classes
   */
  Class<?>[] groups() default {};

  /**
   * Returns the payload associated with the constraint.
   *
   * @return an array of payload classes
   */
  Class<? extends Payload>[] payload() default {};
}
