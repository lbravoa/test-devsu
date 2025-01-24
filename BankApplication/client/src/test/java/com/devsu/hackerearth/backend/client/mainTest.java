package com.devsu.hackerearth.backend.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.devsu.hackerearth.backend.client.controller.ClientController;
import com.devsu.hackerearth.backend.client.model.dto.ClientDto;
import com.devsu.hackerearth.backend.client.model.dto.PartialClientDto;
import com.devsu.hackerearth.backend.client.service.ClientService;

@SpringBootTest
public class mainTest {

	private ClientService clientService = mock(ClientService.class);
	private ClientController clientController = new ClientController(clientService);

	@Test
	void getAllClientsTest() {
		// Arrange
		List<ClientDto> clients = Arrays.asList(
			new ClientDto(1L, "1", "Name", "Password", "Gender", 1, "Address", "9999999999", true),
			new ClientDto(2L, "2", "Name", "Password", "Gender", 1, "Address", "9999999999", true));
		when(clientService.getAll()).thenReturn(clients);

		// Act
		ResponseEntity<List<ClientDto>> response = clientController.getAll();

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(clients, response.getBody());
	}

	@Test
	void getClientTest() {
		// Arrange
		ClientDto clientDto = new ClientDto(1L, "1", "Name", "Password", "Gender", 1, "Address", "9999999999", true);
		when(clientService.getById(clientDto.getId())).thenReturn(clientDto);

		// Act
		ResponseEntity<ClientDto> response = clientController.get(clientDto.getId());

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(clientDto, response.getBody());
	}

	@Test
	void getClientNotFoundTest() {
		// Arrange
		ClientDto clientDto = new ClientDto(1L, "1", "Name", "Password", "Gender", 1, "Address", "9999999999", true);
		when(clientService.getById(clientDto.getId())).thenReturn(clientDto);

		// Act
		ResponseEntity<ClientDto> response = clientController.get(-1L);

		// Assert
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	void createClientTest() {
		// Arrange
		ClientDto newClient = new ClientDto(1L, "1", "Name", "Password", "Gender", 1, "Address", "9999999999", true);
		ClientDto createdClient = new ClientDto(1L, "1", "Name", "Password", "Gender", 1, "Address", "9999999999", true);
		when(clientService.create(newClient)).thenReturn(createdClient);

		// Act
		ResponseEntity<ClientDto> response = clientController.create(newClient);

		// Assert
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(createdClient, response.getBody());
	}

	@Test
	void updateClientTest() {
		// Arrange
		ClientDto client = new ClientDto(1L, "1", "Name", "Password", "Gender", 1, "Address", "9999999999", true);
		ClientDto updatedClient = new ClientDto(1L, "1", "Name", "Password", "Gender", 1, "new answer", "9999999999", true);
		when(clientService.getById(client.getId())).thenReturn(client);
		when(clientService.update(client)).thenReturn(updatedClient);

		// Act
		ResponseEntity<ClientDto> response = clientController.update(updatedClient.getId(), updatedClient);

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(updatedClient, response.getBody());
	}

	@Test
	void updateClientNotFoundTest() {
		// Arrange
		ClientDto client = new ClientDto(1L, "1", "Name", "Password", "Gender", 1, "Address", "9999999999", true);
		ClientDto updatedClient = new ClientDto(1L, "1", "Name", "Password", "Gender", 1, "new answer", "9999999999", true);
		when(clientService.getById(client.getId())).thenReturn(client);
		when(clientService.update(client)).thenReturn(updatedClient);

		// Act
		ResponseEntity<ClientDto> response = clientController.update(-1L, updatedClient);

		// Assert
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	void partialUpdateClientTest() {
		// Arrange
		ClientDto client = new ClientDto(1L, "1", "Name", "Password", "Gender", 1, "Address", "9999999999", true);
		PartialClientDto partialClient = new PartialClientDto(false);
		ClientDto updatedClient = new ClientDto(1L, "1", "Name", "Password", "Gender", 1, "Address", "9999999999", false);
		when(clientService.getById(client.getId())).thenReturn(client);
		when(clientService.partialUpdate(client.getId(), partialClient)).thenReturn(updatedClient);

		// Act
		ResponseEntity<ClientDto> response = clientController.partialUpdate(updatedClient.getId(), partialClient);

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(updatedClient, response.getBody());
	}

	@Test
	void partialUpdateClientNotFoundTest() {
		// Arrange
		ClientDto client = new ClientDto(1L, "1", "Name", "Password", "Gender", 1, "Address", "9999999999", true);
		PartialClientDto partialClient = new PartialClientDto(false);
		ClientDto updatedClient = new ClientDto(1L, "1", "Name", "Password", "Gender", 1, "Address", "9999999999", false);
		when(clientService.getById(client.getId())).thenReturn(client);
		when(clientService.partialUpdate(client.getId(), partialClient)).thenReturn(updatedClient);

		// Act
		ResponseEntity<ClientDto> response = clientController.partialUpdate(-1L, partialClient);

		// Assert
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
    void deleteClientTest() {
        // Arrange
        ClientDto client = new ClientDto(1L, "1", "Name", "Password", "Gender", 1, "Address", "9999999999", true);
        when(clientService.getById(client.getId())).thenReturn(client);

        // Act
        ResponseEntity<Void> response = clientController.delete(client.getId());

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void deleteClientNotFoundTest() {
        // Arrange
        ClientDto client = new ClientDto(1L, "1", "Name", "Password", "Gender", 1, "Address", "9999999999", true);
        when(clientService.getById(client.getId())).thenReturn(client);

        // Act
        ResponseEntity<Void> response = clientController.delete(-1L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
