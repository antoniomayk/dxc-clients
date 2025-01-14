package io.github.antoniomayk.dxc.clients.validator;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import io.github.antoniomayk.dxc.clients.constraint.PhoneNumber;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validator for phone numbers.
 *
 * <p>It checks if a given char sequence represents a valid phone number using {@link
 * PhoneNumberUtil} to parse and validate phone numbers. If the number cannot be parsed or is
 * determined to be invalid, the validation fails.
 *
 * @author Antonio Mayk
 * @since 0.1
 */
public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, CharSequence> {
  private static final PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

  @Override
  public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
    try {
      final var phoneNumber = phoneUtil.parseAndKeepRawInput(value, null);
      return phoneUtil.isValidNumber(phoneNumber);
    } catch (NumberParseException e) {
      context.buildConstraintViolationWithTemplate("Number not valid for the specified region.");
      return false;
    }
  }
}
