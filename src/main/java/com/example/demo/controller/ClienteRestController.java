package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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

import com.example.demo.entity.Address;
import com.example.demo.entity.Cliente;
import com.example.demo.services.IAddressService;
import com.example.demo.services.IClienteService;

@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ClienteRestController {

	@Autowired
	private IClienteService clienteService;
	
	@Autowired
	private IAddressService addressService;
	
	@GetMapping("/clientes")
	public List<Cliente> index(){
		return clienteService.findAll();
	}
	
	@GetMapping("/clientes/page/{page}")
	public Page<Cliente> index(@PathVariable Integer page){
		Pageable pageable = PageRequest.of(page, 4);
		return clienteService.findAll(pageable);
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
	
	@GetMapping("/clientes/address/{id}")
	public ResponseEntity<?> showByAddress(@PathVariable Long id) {

		Cliente cliente =null;
		Map<String, Object> response = new HashMap<>();
		
	try {
			cliente = clienteService.findByaddress_id(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta del cliente por direccion");
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
	public ResponseEntity<?> create(@Valid @RequestBody Cliente cliente, BindingResult result) {
		/*
		 * On this method was added constrains in the entity to be more realistic
		 * to the real database flow
		 */
		Address address = null;
		Cliente clienteNew =null;
		Map<String, Object> response = new HashMap<>();
		
		
		
	
		/**Block to validate the Json recieved from angular with the rules on the Entity **/
		
		if(result.hasErrors()) { //check if there is errors on the binding result
			
			/**First way to get the errors**/
			/*List<String> errors = new 	ArrayList<>();
			
			for (FieldError err: result.getFieldErrors()){
				errors.add("El campo '" +  err.getField() + "'  "+ err.getDefaultMessage());
			}*/
			/**End of first way**/
			
			/**Second way that come after java 8**/
			
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" +  err.getField() + "'  "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			 
			/**End second way	**/
			response.put("errors ", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		/**End of block to validate*/
		
		if(cliente.getAddress()!=null) {
			try {
			address = addressService.save(cliente.getAddress());
			clienteNew = clienteService.save(cliente);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al registrar en el servidor");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}else {
		
		try {
			clienteNew = clienteService.save(cliente);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al registrar en el servidor");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		}
		
		response.put("mensaje", "Cliente registrado con éxito");
		response.put("cliente", clienteNew);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	
	@PutMapping("/clientes/{id}")//The @Valid has to be before the @Request. BindingResult must to be after it but before any variable
	public ResponseEntity<?> update(@Valid @RequestBody Cliente cliente , BindingResult result, @PathVariable Long id) {
		/*
		 * For this method is a combination between the get and create
		 * first the validation of finding the Cliente and the right updating
		 * of the same
		 */
		Cliente clienteUpdated =null;
		Map<String, Object> response = new HashMap<>();
		
		/**Start of block for Valid errors**/
		if(result.hasErrors()) { 
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" +  err.getField() + "'  "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors ", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}	
		/**End of block for Valid errors**/	
		
		Cliente clienteActual = clienteService.findById(id);
		
		/**Start Validation of object found**/
		if(clienteActual == null) {
			response.put("mensaje", "Error: no se pudo editar el cliente con el ID 	: ".concat(id.toString().concat(" No existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		/**End Validation of object found**/
		
		/**Start of Block for apply the update or catch the error**/
		try {
			clienteActual.setName(cliente.getName());
			clienteActual.setLastName(cliente.getLastName());
			clienteActual.setEmail(cliente.getEmail());
			clienteUpdated = clienteService.save(clienteActual);
			
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al actualizar el Cliente en el servidor");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		/**End of block**/
		
		response.put("mensaje", "Cliente ha sido Actualizado con éxito");
		response.put("cliente", clienteUpdated);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED); 
				
	}
	
	
	@DeleteMapping("/clientes/{id}")
	
	public ResponseEntity<?> delete(@PathVariable Long id){
		Map<String, Object> response = new HashMap<>();
		
		try {
			clienteService.Delete(id);
		}catch(DataAccessException e){
			response.put("mensaje", "Error al Eliminar el Cliente en el servidor");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El cliente ha sido eliminado con exito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK); 
	}
	
	


	
}




