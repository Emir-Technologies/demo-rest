package com.example.demo.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.IClientesDao;
import com.example.demo.entity.Cliente;

@Service
public class ClienteServiceImpl implements IClienteService{

	@Autowired
	private IClientesDao clienteDao;
	
	@Override
	@Transactional
	public List<Cliente> findAll() {
		return (List<Cliente>) clienteDao.findAll();
	}

}
