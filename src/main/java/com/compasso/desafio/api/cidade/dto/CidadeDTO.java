package com.compasso.desafio.api.cidade.dto;

import org.modelmapper.ModelMapper;

import com.compasso.desafio.api.cidade.model.Cidade;

import lombok.Data;

@Data
public class CidadeDTO {
	
	private Long id;
	private String nome;
	private String estado;
	
	public static CidadeDTO create(Cidade c) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(c, CidadeDTO.class);
	}

}
