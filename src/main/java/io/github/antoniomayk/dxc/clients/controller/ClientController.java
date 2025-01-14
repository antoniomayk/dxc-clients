package io.github.antoniomayk.dxc.clients.controller;

import io.github.antoniomayk.dxc.clients.dto.ClientDto;
import io.github.antoniomayk.dxc.clients.entity.Client;
import io.github.antoniomayk.dxc.clients.service.ClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.Collection;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing clients. It provides endpoints for CRUD operations on clients.
 *
 * @author Antonio Mayk
 * @since 0.1
 */
@RestController
@RequestMapping("/api/v1/clients")
@Api(tags = "Client Management")
public class ClientController {
  private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

  private final ClientService clientService;

  public ClientController(ClientService clientService) {
    this.clientService = clientService;
  }

  /**
   * Retrieves all clients.
   *
   * @return A collection of all clients
   */
  @GetMapping
  @ApiOperation(value = "Get all clients", notes = "Retrieves a list of all clients in the system")
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "Successfully retrieved list of clients")})
  public Collection<Client> getAllClients() {
    logger.info("Fetching all clients");
    return clientService.getAllClients();
  }

  /**
   * Creates a new client.
   *
   * @param clientDto The client data transfer object containing the client information
   * @return The created client
   */
  @PostMapping
  @ApiOperation(
      value = "Create a new client",
      notes = "Creates a new client with the provided data")
  @ApiResponses(
      value = {
        @ApiResponse(code = 201, message = "Client successfully created"),
        @ApiResponse(code = 400, message = "Invalid input")
      })
  public ResponseEntity<Client> postClient(
      @ApiParam(value = "Client object to be created", required = true) @Valid @RequestBody
          ClientDto clientDto) {
    logger.info("Creating a new client with data: {}", clientDto);
    final var createdClient = clientService.createClient(clientDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdClient);
  }

  /**
   * Updates an existing client.
   *
   * @param clientId The ID of the client to update
   * @param clientDto The client data transfer object containing the updated client information
   * @return The updated client
   */
  @PutMapping("/{clientId}")
  @ApiOperation(
      value = "Update an existing client",
      notes = "Updates a client with the provided data")
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Client successfully updated"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "Client not found")
      })
  public Client putClient(
      @ApiParam(value = "ID of the client to be updated", required = true) @PathVariable
          Long clientId,
      @ApiParam(value = "Updated client object", required = true) @RequestBody
          ClientDto clientDto) {
    logger.info("Updating client with ID: {} with data: {}", clientId, clientDto);
    return clientService.updateClient(clientId, clientDto);
  }

  /**
   * Deletes a client.
   *
   * @param clientId The ID of the client to delete
   */
  @DeleteMapping("/{clientId}")
  @ApiOperation(value = "Delete a client", notes = "Deletes a client with the specified ID")
  @ApiResponses(
      value = {
        @ApiResponse(code = 204, message = "Client successfully deleted"),
        @ApiResponse(code = 404, message = "Client not found")
      })
  public void deleteClient(
      @ApiParam(value = "ID of the client to be deleted", required = true) @PathVariable
          Long clientId) {
    logger.info("Deleting client with ID: {}", clientId);
    clientService.deleteClient(clientId);
  }
}
