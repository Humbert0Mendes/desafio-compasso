package com.compasso.desafio.api.cidade.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Cidade {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Column(name = "nome", unique = false, nullable = false)
	private String nome;

	@Column(name = "estado", unique = false, nullable = false)
	private String estado;

}
