package com.compasso.desafio.api.cliente.controller;

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

import com.compasso.desafio.api.cliente.dto.ClienteDTO;
import com.compasso.desafio.api.cliente.model.Cliente;
import com.compasso.desafio.api.cliente.services.ClienteService;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

	@Autowired
	ClienteService clienteService;

	@GetMapping
	public ResponseEntity<List<ClienteDTO>> buscarClientes() {
		
		return ResponseEntity.ok(clienteService.getClientes());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscarClientePorId(@PathVariable(value = "id") long id) {
		Optional<ClienteDTO> cliente = clienteService.getClienteById(id);
		
		return cliente
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<ClienteDTO>> buscarClientePorNome(@PathVariable(value = "nome") String nome) {
		List<ClienteDTO> cliente = clienteService.getClienteByNome(nome);
		
		return cliente.isEmpty() ? 
				ResponseEntity.noContent().build() : 
				ResponseEntity.ok(cliente);
	}
	
	@PostMapping()
	public ResponseEntity<?> salvaCliente(@RequestBody @Validated Cliente cliente) {
		try {
			ClienteDTO cd = clienteService.save(cliente);
			URI location = getUri(cd.getId());
			
			return ResponseEntity.created(location).build();
			
		} catch (Exception e) {
			
			return ResponseEntity.badRequest().build();
		}
	}
	
	private URI getUri(Long id) {
		return ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(id).toUri();
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> deletaCliente(@PathVariable("id") Long id) {
		
		boolean ok = clienteService.delete(id);
		return ok ?
				ResponseEntity.ok().build():
				ResponseEntity.notFound().build();
	}
	
	@PutMapping("{id}")
	public ResponseEntity<?> atualizaCliente(@PathVariable("id") Long id, @RequestBody Cliente cliente){
		
		cliente.setId(id);
		ClienteDTO cd = clienteService.update(id, cliente);
		
		return cd != null ?
				ResponseEntity.ok(cd):
				ResponseEntity.notFound().build();
	}
}
