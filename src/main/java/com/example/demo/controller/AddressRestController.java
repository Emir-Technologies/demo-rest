package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Address;
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
	

}
