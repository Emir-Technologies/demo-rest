package com.example.demo.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.entity.Address;


public interface IAddressService {

	public List<Address> findAll();
	public Address findById(Long id);
	public Address save(Address cliente);
	public void Delete(Long id);
	public Page<Address> findAll(Pageable page);
	
}
