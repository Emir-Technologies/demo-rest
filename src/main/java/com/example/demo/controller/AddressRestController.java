package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Address;
import com.example.demo.entity.Cliente;
import com.example.demo.services.IAddressService;

@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class AddressRestController {

	@Autowired
	private IAddressService addressService;
	
	@GetMapping("/address")
	public List<Address> index(){
		return addressService.findAll();
	}
	
	
	@GetMapping("/address/{id}")
	public ResponseEntity<?> show(@PathVariable Long id){
		Address address = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			address = addressService.findById(id);
		}catch(DataAccessException e) {
			response.put("message", "Error at server to try access");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(address==null) {
			response.put("message", "The Address with the Id: ".concat(id.toString().concat(" Doesn't exist on the Database")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Address>(address, HttpStatus.OK);
	}

	
	@PostMapping("/address")
	public ResponseEntity<?> create(@RequestBody Address address){
		
		Address newAddress = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			newAddress = addressService.save(address);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al registrar en el servidor");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mesage", "Cliente registrado con éxito");
		response.put("address", newAddress);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	

	
@PutMapping("/address/{id}")
	public ResponseEntity<?> update(@RequestBody Address address , BindingResult result, @PathVariable Long id) {
		Address addressUpdated =null;
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
		
		Address addressActual = addressService.findById(id);
		
		/**Start Validation of object found**/
		if(addressActual == null) {
			response.put("mensaje", "Error: no se pudo editar el cliente con el ID 	: ".concat(id.toString().concat(" No existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		/**End Validation of object found**/
		
		/**Start of Block for apply the update or catch the error**/
		try {
			addressActual.setCity(address.getCity());
			addressActual.setCountry(address.getCountry());
			addressActual.setStreet(address.getStreet());
			addressUpdated = addressService.save(addressActual);
			
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al actualizar el Cliente en el servidor");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		/**End of block**/
		
		response.put("mensaje", "Address ha sido Actualizado con éxito");
		response.put("address", addressUpdated);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED); 
				
	}
	
	
	@DeleteMapping("/address/{id}")
	
	public ResponseEntity<?> delete(@PathVariable Long id){
		Map<String, Object> response = new HashMap<>();
		
		try {
			addressService.Delete(id);
		}catch(DataAccessException e){
			response.put("mensaje", "Error al Eliminar el address en el servidor");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El cliente ha sido eliminado con exito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK); 
	}
	
	
	
	
}
