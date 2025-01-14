package io.github.antoniomayk.dxc.clients.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import io.github.antoniomayk.dxc.clients.dto.ClientDto;
import io.github.antoniomayk.dxc.clients.entity.Client;
import io.github.antoniomayk.dxc.clients.exception.ClientDeletedException;
import io.github.antoniomayk.dxc.clients.exception.ClientNotFoundException;
import io.github.antoniomayk.dxc.clients.repository.ClientRepository;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;

class ClientServiceTest {
  @Mock private ClientRepository clientRepository;

  @Mock private Validator validator;

  @Mock private MessageSource messageSource;

  @InjectMocks private ClientService clientService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void getAllClients_ShouldReturnAllActiveClients() {
    final var activeClients = Arrays.asList(new Client(), new Client());
    when(clientRepository.findAllActiveClients()).thenReturn(activeClients);

    final var result = clientService.getAllClients();

    assertEquals(2, result.size());
    verify(clientRepository).findAllActiveClients();
  }

  @Test
  void createClient_WithValidData_ShouldCreateAndReturnClient() {
    final var clientDto = new ClientDto();
    final var savedClient = new Client();
    when(validator.validate(clientDto)).thenReturn(Collections.emptySet());
    when(clientRepository.save(any(Client.class))).thenReturn(savedClient);

    final var result = clientService.createClient(clientDto);

    assertNotNull(result);
    verify(clientRepository).save(any(Client.class));
  }

  @Test
  void createClient_WithInvalidData_ShouldThrowConstraintViolationException() {
    final var clientDto = new ClientDto();
    final var violations = new HashSet<ConstraintViolation<ClientDto>>();
    violations.add(mock(ConstraintViolation.class));
    when(validator.validate(clientDto)).thenReturn(violations);

    assertThrows(ConstraintViolationException.class, () -> clientService.createClient(clientDto));
  }

  @Test
  void updateClient_WithValidDataAndExistingClient_ShouldUpdateAndReturnClient() {
    final var clientId = 1L;
    final var clientDto = new ClientDto();
    final var existingClient = new Client();
    when(clientRepository.existsByIdAndNotDeleted(clientId)).thenReturn(true);
    when(validator.validate(clientDto)).thenReturn(Collections.emptySet());
    when(clientRepository.findById(clientId)).thenReturn(Optional.of(existingClient));
    when(clientRepository.save(existingClient)).thenReturn(existingClient);

    final var result = clientService.updateClient(clientId, clientDto);

    assertNotNull(result);
    verify(clientRepository).save(existingClient);
  }

  @Test
  void updateClient_WithDeletedClient_ShouldThrowClientDeletedException() {
    final var clientId = 1L;
    final var clientDto = new ClientDto();
    when(clientRepository.existsByIdAndNotDeleted(clientId)).thenReturn(false);
    when(messageSource.getMessage(anyString(), any(Object[].class), any()))
        .thenReturn("Client with ID {0} has been deleted");

    final var exception =
        assertThrows(
            ClientDeletedException.class, () -> clientService.updateClient(clientId, clientDto));

    assertEquals("Client with ID {0} has been deleted", exception.getMessage());
    verify(clientRepository).existsByIdAndNotDeleted(clientId);
    verifyNoMoreInteractions(clientRepository);
  }

  @Test
  void updateClient_WithNonExistentClient_ShouldThrowClientNotFoundException() {
    final var clientId = 1L;
    final var clientDto = new ClientDto();
    when(clientRepository.existsByIdAndNotDeleted(clientId)).thenReturn(true);
    when(validator.validate(clientDto)).thenReturn(Collections.emptySet());
    when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

    assertThrows(
        ClientNotFoundException.class, () -> clientService.updateClient(clientId, clientDto));
  }

  @Test
  void deleteClient_WithExistingClient_ShouldDeactivateClient() {
    final var clientId = 1L;
    when(clientRepository.existsByIdAndNotDeleted(clientId)).thenReturn(true);

    clientService.deleteClient(clientId);

    verify(clientRepository).deactivateClient(clientId);
  }

  @Test
  void deleteClient_WithNonExistentOrAlreadyDeletedClient_ShouldNotDeactivateClient() {
    final var clientId = 1L;
    when(clientRepository.existsByIdAndNotDeleted(clientId)).thenReturn(false);

    clientService.deleteClient(clientId);

    verify(clientRepository, never()).deactivateClient(clientId);
  }
}
