package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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

import com.example.demo.entity.Cliente;
import com.example.demo.services.IClienteService;

@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ClienteRestController {

	@Autowired
	private IClienteService clienteService;
	
	@GetMapping("/clientes")
	public List<Cliente> index(){
		return clienteService.findAll();
	}
	
	@GetMapping("/clientes/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		/*
		 * ResponseEntity its used to return a Json response with the content
		 *or the error in the transaction/query and their respective status code.
		 *  note: between <> in ResponseEntity can put the object or Map to response
		 *  use the sign ? to allow any of them in the response 
		 */
		Cliente cliente =null;
		Map<String, Object> response = new HashMap<>();
		
		/*
		 * In order to prepare the user for an errors on the server will use try
		 * to can catch any Internal server error.
		 */
		try {
			cliente = clienteService.findById(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta al servidor");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		//Finally will make a confirmation about the content on the object cliente
		if(cliente==null) {
			response.put("mensaje", "El cliente ID: ".concat(id.toString().concat(" No existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		//Each return give the content: message or object and the respective status
		return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
	}
	
	@PostMapping("/clientes")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> create(@RequestBody Cliente cliente) {
		/*
		 * On this method was added constrains in the entity to be more realistic
		 * to the real database flow
		 */
		
		Cliente clienteNew =null;
		Map<String, Object> response = new HashMap<>();
		

		try {
			clienteNew = clienteService.save(cliente);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al registrar en el servidor");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Cliente registrado con éxito");
		response.put("cliente", clienteNew);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	
	@PutMapping("/clientes/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente update(@RequestBody Cliente cliente, @PathVariable Long id) {
		Cliente clienteActual = clienteService.findById(id);
		
		clienteActual.setName(cliente.getName());
		clienteActual.setLastName(cliente.getLastName());
		clienteActual.setEmail(cliente.getEmail());
		
		return clienteService.save(clienteActual);
	}
	
	
	@DeleteMapping("/clientes/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		clienteService.Delete(id);
	}
	
	


	
}




