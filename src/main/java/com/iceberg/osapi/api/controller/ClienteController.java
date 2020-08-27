package com.iceberg.osapi.api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.iceberg.osapi.domain.model.Cliente;
import com.iceberg.osapi.domain.repository.ClienteRepository;
import com.iceberg.osapi.domain.service.CadastroClienteService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@Api(value="API REST Ordem de Serviço")
@CrossOrigin("*")
public class ClienteController {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private CadastroClienteService cadastroCliente;
	
	
	@GetMapping("/clientes")
	@ApiOperation(value="Retorna lista de Ordens de Serviço")
	public List<Cliente> Listar() {
		return clienteRepository.findAll();
	}
	
	@GetMapping("/clientes/{clienteId}")
	@ApiOperation(value="Retorna cliente pelo ID")	
	public ResponseEntity<Cliente>  buscar(@PathVariable Long clienteId) {
		 Optional<Cliente> cliente = clienteRepository.findById(clienteId);		
		 
		 if(cliente.isPresent()) {
			 return ResponseEntity.ok(cliente.get());
		 }
		 
		 return ResponseEntity.notFound().build();
	}
	
	@PostMapping("/clientes")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value="Cria novo Cliente")
	public Cliente adicionar(@Valid @RequestBody Cliente cliente) {
		return cadastroCliente.salvar(cliente);
	}
	
	@PutMapping("/clientes/{clientId}")
	@ApiOperation(value="Atualiza dados do Cliente")
	public ResponseEntity<Cliente> atualizar(@Valid @PathVariable Long clientId, @RequestBody Cliente cliente) {
		if(!clienteRepository.existsById(clientId)) {
			return ResponseEntity.notFound().build();			
		}
		
		cliente.setId(clientId);
		cliente = cadastroCliente.salvar(cliente);
		
		return ResponseEntity.ok(cliente);
		
	}
	
	@DeleteMapping("/clientes/{clientId}")
	@ApiOperation(value="Deleta cliente")
	public ResponseEntity<Void> excluir(@PathVariable Long clientId) {
		if(!clienteRepository.existsById(clientId)) {
			return ResponseEntity.notFound().build();			
		}		
		cadastroCliente.excluir(clientId);		
		return ResponseEntity.noContent().build();
		
	}
	


}
