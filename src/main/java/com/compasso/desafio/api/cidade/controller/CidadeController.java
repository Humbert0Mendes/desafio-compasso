package com.compasso.desafio.api.cidade.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.compasso.desafio.api.cidade.dto.CidadeDTO;
import com.compasso.desafio.api.cidade.model.Cidade;
import com.compasso.desafio.api.cidade.services.CidadeService;

@RestController
@RequestMapping("/api/cidades")
public class CidadeController {

	@Autowired
	CidadeService cidadeService;

	@GetMapping
	public ResponseEntity<List<CidadeDTO>> buscarCidades() {
		return ResponseEntity.ok(cidadeService.getCidades());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> buscaCidadePorId(@PathVariable(value = "id") long id) {
		Optional<CidadeDTO> cidade = cidadeService.getCidadeById(id);

		return cidade
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/estado/{estado}")
	public ResponseEntity<List<CidadeDTO>> buscaCidadePorEstado(@PathVariable(value = "estado") String estado) {
		List<CidadeDTO> cidades = cidadeService.getCidadeByEstado(estado);

		return cidades.isEmpty() ? 
				ResponseEntity.noContent().build() : 
				ResponseEntity.ok(cidades);
	}

	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<CidadeDTO>> buscaCidadePorNome(@PathVariable(value = "nome") String nome) {
		List<CidadeDTO> cidades = cidadeService.getCidadeByNome(nome);

		return cidades.isEmpty() ? 
				ResponseEntity.noContent().build() : 
				ResponseEntity.ok(cidades);
	}

	@PostMapping()
	public ResponseEntity<?> cadastraCidade(@RequestBody @Validated Cidade cidade) {
		try {
			CidadeDTO cd = cidadeService.save(cidade);
			
			URI location = getUri(cd.getId());
			return ResponseEntity.created(location).build();
			
		}catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	private URI getUri(Long id) {
		return ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(id).toUri();
	}

	@DeleteMapping("{id}")
	public ResponseEntity<?> deletaCidade(@PathVariable("id") long id) {
		
		boolean ok = cidadeService.deleteById(id);
		
		return ok ?
				ResponseEntity.ok().build():
				ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizaCidade(@PathVariable("id") Long id, @RequestBody Cidade cidade){
		
		cidade.setId(id);
		
		CidadeDTO cd = cidadeService.update(cidade, id);
		
		return cd != null ?
				ResponseEntity.ok(cd):
				ResponseEntity.notFound().build();
	}

}
