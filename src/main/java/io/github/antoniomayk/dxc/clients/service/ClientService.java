package io.github.antoniomayk.dxc.clients.service;

import io.github.antoniomayk.dxc.clients.dto.ClientDto;
import io.github.antoniomayk.dxc.clients.entity.Client;
import io.github.antoniomayk.dxc.clients.exception.ClientDeletedException;
import io.github.antoniomayk.dxc.clients.exception.ClientNotFoundException;
import io.github.antoniomayk.dxc.clients.repository.ClientRepository;
import java.util.Collection;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

/**
 * Service class for managing client operations.
 *
 * @author Antonio Mayk
 * @since 0.1
 */
@Service
public class ClientService {
  private static final Logger logger = LoggerFactory.getLogger(ClientService.class);

  private final ClientRepository clientRepository;
  private final Validator validator;
  private final MessageSource messageSource;

  ClientService(
      ClientRepository clientRepository, Validator validator, MessageSource messageSource) {
    this.clientRepository = clientRepository;
    this.validator = validator;
    this.messageSource = messageSource;
  }

  private void validateClientDto(ClientDto clientDto) {
    logger.debug("Validating client DTO: {}", clientDto);
    final var violations = validator.validate(clientDto);
    if (!violations.isEmpty()) {
      logger.warn("Validation failed for client DTO: {}", violations);
      throw new ConstraintViolationException(violations);
    }
  }

  /**
   * Retrieves all active clients from the repository.
   *
   * @return a collection representing all active clients.
   */
  public Collection<Client> getAllClients() {
    logger.info("Retrieving all clients");
    final var activeClient = clientRepository.findAllActiveClients();
    logger.info("Found {} active clients", activeClient.size());
    return activeClient;
  }

  /**
   * Creates a new client based on the provided client data transfer object.
   *
   * <p>This method validates the input, creates a new Client entity, saves it to the repository,
   * and returns the saved client.
   *
   * @param clientDto the DTO containing the client information to be created.
   * @return the newly created and saved client entity
   * @throws ConstraintViolationException if the provided clientDto fails validation.
   */
  public Client createClient(ClientDto clientDto) {
    validateClientDto(clientDto);

    logger.info("Creating a new client with data: {}", clientDto);

    final var client = new Client();
    client.setFullName(clientDto.getFullName());
    client.setPhoneNumber(clientDto.getPhoneNumber());
    client.setEmail(clientDto.getEmail());

    logger.debug("Saving new client to repository");
    final var savedClient = clientRepository.save(client);

    logger.info("Client created successfully with ID: {}", savedClient.getId());
    return savedClient;
  }

  /**
   * Updates an existing client with the provided information.
   *
   * <p>This method checks if the client exists and is not deleted, validates the input data,
   * updates the client's information, and saves the changes to the repository.
   *
   * @param clientId the ID of the client to be updated
   * @param clientDto the DTO containing the updated client information
   * @return the updated Client entity
   * @throws ClientDeletedException if the client with the given ID has been deleted
   * @throws ClientNotFoundException if no client is found with the given ID
   * @throws ConstraintViolationException if the provided clientDto fails validation
   */
  public Client updateClient(Long clientId, ClientDto clientDto) {
    final var isDeleted = clientRepository.existsByIdAndNotDeleted(clientId);
    if (!isDeleted) {
      logger.warn("Attempt to update deleted client with ID: {}", clientId);
      throw new ClientDeletedException(
          messageSource.getMessage(
              "error.ClientDeletedException",
              new Object[] {clientId},
              LocaleContextHolder.getLocale()));
    }

    validateClientDto(clientDto);

    logger.debug("Updating client with ID: {}", clientId);
    final var client =
        clientRepository
            .findById(clientId)
            .orElseThrow(
                () ->
                    new ClientNotFoundException(
                        messageSource.getMessage(
                            "error.ClientNotFoundException",
                            new Object[] {clientId},
                            LocaleContextHolder.getLocale())));

    client.setEmail(clientDto.getEmail());
    client.setFullName(clientDto.getFullName());
    client.setPhoneNumber(clientDto.getPhoneNumber());

    logger.info("Updating client with ID: {}", clientId);

    final var updatedClient = clientRepository.save(client);
    logger.info("Client updated with ID: {}", updatedClient.getId());
    return updatedClient;
  }

  /**
   * Deletes a client with the given ID if it exists.
   *
   * @param clientId the ID of the client to delete
   */
  public void deleteClient(Long clientId) {
    logger.info("Attempting to delete client with ID: {}", clientId);
    final var isDeleted = clientRepository.existsByIdAndNotDeleted(clientId);
    if (!isDeleted) {
      logger.warn("Client with ID: {} not found, nothing to delete", clientId);
      return;
    }
    clientRepository.deactivateClient(clientId);
    logger.info("Client with ID: {} deleted", clientId);
  }
}
