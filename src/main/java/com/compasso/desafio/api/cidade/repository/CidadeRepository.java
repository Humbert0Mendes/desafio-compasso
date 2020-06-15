package com.compasso.desafio.api.cidade.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.compasso.desafio.api.cidade.model.Cidade;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long> {

	//Cidade findById(long id);

	List<Cidade> findByEstado(String estado);

	List<Cidade> findByNome(String nome);

}
