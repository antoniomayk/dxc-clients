package io.github.antoniomayk.dxc.clients.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.antoniomayk.dxc.clients.dto.ClientDto;
import io.github.antoniomayk.dxc.clients.entity.Client;
import io.github.antoniomayk.dxc.clients.repository.ClientRepository;
import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ClientControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @MockBean private ClientRepository clientRepository;

  private Client testClient;
  private ClientDto testClientDto;

  @BeforeEach
  void setUp() {
    testClient = new Client();
    testClient.setId(1L);
    testClient.setFullName("John Doe");
    testClient.setEmail("john.doe@example.com");
    testClient.setPhoneNumber("+5588988397489");

    testClientDto = new ClientDto();
    testClientDto.setFullName(testClient.getFullName());
    testClientDto.setEmail(testClient.getEmail());
    testClientDto.setPhoneNumber(testClient.getPhoneNumber());
  }

  @Test
  void getAllClients() throws Exception {
    when(clientRepository.findAllActiveClients()).thenReturn(Arrays.asList(testClient));

    mockMvc
        .perform(get("/api/v1/clients").with(httpBasic("admin", "admin")))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$[0].fullName").value(testClient.getFullName()))
        .andExpect(jsonPath("$[0].email").value(testClient.getEmail()))
        .andExpect(jsonPath("$[0].phoneNumber").value(testClient.getPhoneNumber()));
  }

  @Test
  void postClient() throws Exception {
    when(clientRepository.save(any(Client.class))).thenReturn(testClient);

    mockMvc
        .perform(
            post("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testClientDto))
                .with(httpBasic("admin", "admin")))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.fullName").value(testClient.getFullName()))
        .andExpect(jsonPath("$.email").value(testClient.getEmail()))
        .andExpect(jsonPath("$.phoneNumber").value(testClient.getPhoneNumber()));
  }

  @Test
  void putClient() throws Exception {
    when(clientRepository.findById(testClient.getId())).thenReturn(Optional.of(testClient));
    when(clientRepository.existsByIdAndNotDeleted(testClient.getId())).thenReturn(true);
    when(clientRepository.save(any(Client.class))).thenReturn(testClient);

    mockMvc
        .perform(
            put("/api/v1/clients/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testClientDto))
                .with(httpBasic("admin", "admin")))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(testClient.getId()))
        .andExpect(jsonPath("$.fullName").value(testClient.getFullName()))
        .andExpect(jsonPath("$.email").value(testClient.getEmail()))
        .andExpect(jsonPath("$.phoneNumber").value(testClient.getPhoneNumber()));
  }

  @Test
  void deleteClient() throws Exception {
    when(clientRepository.existsByIdAndNotDeleted(testClient.getId())).thenReturn(true);

    mockMvc
        .perform(delete("/api/v1/clients/1").with(httpBasic("admin", "admin")))
        .andExpect(status().isOk());

    verify(clientRepository, times(1)).deactivateClient(testClient.getId());
  }
}
