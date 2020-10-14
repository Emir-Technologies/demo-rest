package com.example.demo.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.entity.Visitas;

public interface IVisitasService {

	public List<Visitas> findAll();
	public Visitas findById(Long id);
	public Visitas save(Visitas visita);
	public void Delete(Long id);
	public Page<Visitas> findAll(Pageable page);
	
}
