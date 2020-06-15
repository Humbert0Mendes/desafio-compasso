package com.compasso.desafio.api.cliente.dto;

import org.modelmapper.ModelMapper;

import com.compasso.desafio.api.cidade.model.Cidade;
import com.compasso.desafio.api.cliente.model.Cliente;

import lombok.Data;

@Data
public class ClienteDTO {

	private Long id;
	private String nome;
	private String sexo;
	private String dataNasc;
	private int idade;
	private Cidade cidade;

	public static ClienteDTO create(Cliente c) {

		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(c, ClienteDTO.class);
	}

}
