package io.github.antoniomayk.dxc.clients.exception;

/**
 * Exception thrown when a client is not found.
 *
 * @author Antonio Mayk
 * @since 0.1
 */
public class ClientNotFoundException extends RuntimeException {
  public ClientNotFoundException(String message) {
    super(message);
  }
}
