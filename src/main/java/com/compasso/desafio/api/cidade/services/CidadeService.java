package com.compasso.desafio.api.cidade.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.compasso.desafio.api.cidade.dto.CidadeDTO;
import com.compasso.desafio.api.cidade.model.Cidade;
import com.compasso.desafio.api.cidade.repository.CidadeRepository;

@Service
public class CidadeService {

	@Autowired
	CidadeRepository cidadeRepository;

	public List<CidadeDTO> getCidades() {

		return cidadeRepository.findAll().stream()
				.map(CidadeDTO::create).collect(Collectors.toList());
	}
	
	public Optional<CidadeDTO> getCidadeById(Long id){
		
		return cidadeRepository.findById(id).map(CidadeDTO::create);
	}

	public List<CidadeDTO> getCidadeByEstado(String estado) {
		
		return cidadeRepository.findByEstado(estado).stream()
				.map(CidadeDTO::create).collect(Collectors.toList());
	}

	public List<CidadeDTO> getCidadeByNome(String nome) {
		
		return cidadeRepository.findByNome(nome).stream()
				.map(CidadeDTO::create).collect(Collectors.toList());
	}

	public CidadeDTO save(Cidade cidade) {
	
		Assert.isNull(cidade.getId(), "Ao cadastrar uma idade o id não deve ser informado");
		return CidadeDTO.create(cidadeRepository.save(cidade));
	}

	public boolean deleteById(long id) {
		
		if(cidadeRepository.findById(id).isPresent()) {
			cidadeRepository.deleteById(id);
			
			return true;
		}else {
			return false;
		}
	}
	
	public CidadeDTO update(Cidade cidade, Long id) {
		Assert.notNull(id, "Não foi possível atualizar a cidade");
		
		//Busca a cidade no banco de dados
		Optional<Cidade> optional = cidadeRepository.findById(id);
		if(optional.isPresent()) {
			Cidade cd = optional.get();
			
			//Copia as propriedades
			cd.setNome(cidade.getNome());
			cd.setEstado(cidade.getEstado());
			
			//Atualiza a cidade
			cidadeRepository.save(cd);
			
			return CidadeDTO.create(cd);
		}else {
			return null;
		}
	}
}
