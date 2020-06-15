package com.compasso.desafio.api.cliente.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.compasso.desafio.api.cliente.dto.ClienteDTO;
import com.compasso.desafio.api.cliente.model.Cliente;
import com.compasso.desafio.api.cliente.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	ClienteRepository clienteRepository;

	public List<ClienteDTO> getClientes() {
		
		return clienteRepository.findAll().stream()
				.map(ClienteDTO::create).collect(Collectors.toList());
	}

	public Optional<ClienteDTO> getClienteById(long id) {

		return clienteRepository.findById(id).map(ClienteDTO::create);
	}

	public List<ClienteDTO> getClienteByNome(String nome) {
		
		return clienteRepository.findByNome(nome).stream()
				.map(ClienteDTO::create).collect(Collectors.toList());
	}

	public ClienteDTO save(Cliente cliente) {

		Assert.isNull(cliente.getId(), "Ao cadastrar um cliente o id não deve ser informado");

		return ClienteDTO.create(clienteRepository.save(cliente));
	}

	public boolean delete(Long id) {

		if (clienteRepository.findById(id).isPresent()) {
			clienteRepository.deleteById(id);
			return true;
		} else {
			return false;
		}
	}

	public ClienteDTO update(Long id, Cliente cliente) {
		Assert.notNull(id, "Não foi possível atualizar o cliente");

		Optional<Cliente> optional = clienteRepository.findById(id);
		if (optional.isPresent()) {
			Cliente cl = optional.get();

			cl.setNome(cliente.getNome());
			cl.setSexo(cliente.getSexo());
			cl.setIdade(cliente.getIdade());
			cl.setDataNasc(cliente.getDataNasc());
			cl.setCidade(cliente.getCidade());

			clienteRepository.save(cl);
			return ClienteDTO.create(cl);
		} else {
			return null;
		}

	}
}
