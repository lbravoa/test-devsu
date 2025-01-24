package com.devsu.hackerearth.backend.client.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.client.handler.exceptions.RegisterNotFoundException;
import com.devsu.hackerearth.backend.client.model.Client;
import com.devsu.hackerearth.backend.client.model.dto.ClientDto;
import com.devsu.hackerearth.backend.client.model.dto.PartialClientDto;
import com.devsu.hackerearth.backend.client.repository.ClientRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ClientServiceImpl implements ClientService {

	private final ClientRepository clientRepository;
	private final ObjectMapper mapper;

	public ClientServiceImpl(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;

		mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	}

	@Override
	public List<ClientDto> getAll() {
		return clientRepository.findAll()
				.stream()
				.map(o -> mapper.convertValue(o, ClientDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public ClientDto getById(Long id) {
		return mapper.convertValue(clientRepository.findById(id)
				.orElseThrow(() -> new RegisterNotFoundException()), ClientDto.class);
	}

	@Override
	public ClientDto create(ClientDto clientDto) {
		Client client = clientRepository.save(mapper.convertValue(clientDto, Client.class));
		log.debug("Client save result {}", client);

		return mapper.convertValue(client, ClientDto.class);
	}

	@Override
	public ClientDto update(ClientDto clientDto) {
		Client client = clientRepository.save(mapper.convertValue(clientDto, Client.class));
		log.info("Client save result {}", client);

		return mapper.convertValue(client, ClientDto.class);
	}

	@Override
	public ClientDto partialUpdate(Long id, PartialClientDto partialClientDto) {
		Client client = clientRepository.findById(id).orElseThrow(() -> new RegisterNotFoundException());

		ClientDto clientDto = mapper.convertValue(client, ClientDto.class);
		clientDto.setActive(partialClientDto.isActive());

		clientDto = mapper.convertValue(clientRepository.save(mapper.convertValue(clientDto, Client.class)),
				ClientDto.class);

		log.info("Client partial update {}", clientDto);
		return clientDto;
	}

	@Override
	public void deleteById(Long id) {
		clientRepository.deleteById(id);
	}
}
