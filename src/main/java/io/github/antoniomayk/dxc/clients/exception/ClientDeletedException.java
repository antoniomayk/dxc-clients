package io.github.antoniomayk.dxc.clients.exception;

/**
 * Exception thrown when attempting to perform an operation on a deleted client.
 *
 * @author Antonio Mayk
 * @since 0.1
 */
public class ClientDeletedException extends RuntimeException {
  public ClientDeletedException(String message) {
    super(message);
  }
}
