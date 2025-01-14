package io.github.antoniomayk.dxc.clients.dto;

import java.util.Map;

/**
 * Represents an error response containing details about an error occurrence.
 *
 * @author Antonio Mayk
 * @since 0.1
 */
public class ErrorResponse {
  private int status;
  private String error;
  private String message;
  private Map<String, String> errors;

  /**
   * Constructs a new ErrorResponse with the specified details.
   *
   * @param status the HTTP status code of the error
   * @param error a brief description of the error
   * @param message a more detailed message about the error
   * @param errors a map of field-specific errors, where the key is the field name and the value is
   *     the error message
   */
  public ErrorResponse(int status, String error, String message, Map<String, String> errors) {
    this.status = status;
    this.error = error;
    this.message = message;
    this.errors = errors;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Map<String, String> getErrors() {
    return errors;
  }

  public void setErrors(Map<String, String> errors) {
    this.errors = errors;
  }
}
