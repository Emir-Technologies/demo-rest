package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.dao.IVisitasDao;
import com.example.demo.entity.Visitas;

@Service
public class VisitasServiceImpl implements IVisitasService {

	@Autowired
	private IVisitasDao visitasDao;
	
	@Override
	public List<Visitas> findAll() {
		return (List<Visitas>) visitasDao.findAll();
	}

	@Override
	public Visitas findById(Long id) {
		return visitasDao.findById(id).orElse(null);
	}

	@Override
	public Visitas save(Visitas visita) {
		return visitasDao.save(visita);
	}

	@Override
	public void Delete(Long id) {
		visitasDao.deleteById(id);
	}

	@Override
	public Page<Visitas> findAll(Pageable page) {
		// TODO Auto-generated method stub
		return null;
	}

}
