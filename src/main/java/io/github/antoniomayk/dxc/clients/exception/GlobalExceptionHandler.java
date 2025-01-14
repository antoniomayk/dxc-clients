package io.github.antoniomayk.dxc.clients.exception;

import io.github.antoniomayk.dxc.clients.dto.ErrorResponse;
import java.util.HashMap;
import java.util.Locale;
import javax.validation.ConstraintViolationException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for the application. Provides centralized exception handling across
 * all @RequestMapping methods through @ExceptionHandler methods.
 *
 * @author Antonio Mayk
 * @since 0.1
 */
@ControllerAdvice
public class GlobalExceptionHandler {

  private final MessageSource messageSource;

  GlobalExceptionHandler(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  @ExceptionHandler(ClientDeletedException.class)
  ResponseEntity<ErrorResponse> handleClientInactiveException(
      ClientDeletedException ex, Locale locale) {
    final var message = messageSource.getMessage("error.ClientDeletedException", null, locale);
    final var errorResponse =
        new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Not Found", message, null);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  ResponseEntity<ErrorResponse> handleValidationExceptions(
      MethodArgumentNotValidException ex, Locale locale) {
    final var errors = new HashMap<String, String>();
    ex.getBindingResult()
        .getFieldErrors()
        .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

    final var message =
        messageSource.getMessage("error.MethodArgumentNotValidException", null, locale);
    final var errorResponse =
        new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Bad Request", message, errors);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  ResponseEntity<ErrorResponse> handleConstraintViolationException(
      ConstraintViolationException ex, Locale locale) {
    final var errors = new HashMap<String, String>();
    ex.getConstraintViolations()
        .forEach(
            violation ->
                errors.put(violation.getPropertyPath().toString(), violation.getMessage()));

    final var message =
        messageSource.getMessage("error.ConstraintViolationException", null, locale);
    final var errorResponse =
        new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Bad Request", message, errors);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }
}
