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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Address;
import com.example.demo.entity.Cliente;
import com.example.demo.entity.Visitas;
import com.example.demo.services.IAddressService;
import com.example.demo.services.IVisitasService;

@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class VisitasRestController {

	@Autowired
	private IVisitasService visitasService;
	
	@GetMapping("/visitas")
	public List<Visitas> index(){
		return visitasService.findAll();
	}
	
	
	@GetMapping("/visitas/{id}")
	public ResponseEntity<?> show(@PathVariable Long id){
		Visitas visitas = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			visitas = visitasService.findById(id);
		}catch(DataAccessException e) {
			response.put("message", "Error at server to try access");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(visitas==null) {
			response.put("message", "The Address with the Id: ".concat(id.toString().concat(" Doesn't exist on the Database")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Visitas>(visitas, HttpStatus.OK);
	}
	
	@PostMapping("/visitas")
	public ResponseEntity<?> create(@RequestBody Visitas visita, BindingResult result) {
			
		Address newAddress = null;
		Cliente clienteNew = null;
		Visitas newVisitas = null;
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
		
			try {
//			address = addressService.save(cliente.getAddress());
			newVisitas = visitasService.save(visita);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al registrar en el servidor");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		
		
		response.put("mensaje", "Vsita registrado con éxito");
		response.put("Visita", newVisitas);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	
}
