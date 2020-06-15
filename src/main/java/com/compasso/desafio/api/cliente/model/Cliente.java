package com.compasso.desafio.api.cliente.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.compasso.desafio.api.cidade.model.Cidade;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Entity
@Data
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	@Column(name = "nome", unique = false, nullable = false)
	private String nome;
	
	@Column(name = "sexo", unique = false, nullable = false)
	private String sexo;
	
	@Column(name = "dataNasc", unique = false, nullable = false)
	private String dataNasc;
	
	@Column(name = "idade", unique = false, nullable = false)
	private int idade;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference
	@JoinColumn(name = "id_cidade", referencedColumnName = "id")
	private Cidade cidade;

}
