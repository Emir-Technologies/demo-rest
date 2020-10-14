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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Address;
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
	
}
