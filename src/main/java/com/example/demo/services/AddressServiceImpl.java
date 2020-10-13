package com.example.demo.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.dao.IAddressDao;
import com.example.demo.entity.Address;

@Service
public class AddressServiceImpl implements IAddressService{

	@Autowired
	private IAddressDao addressDao;
	
	@Override	
	@Transactional
	public List<Address> findAll() {
		return (List<Address>) addressDao.findAll(); 
	}

	@Override
	@Transactional
	public Address findById(Long id) {
		return addressDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Address save(Address address) {
		return addressDao.save(address);
	}

	@Override
	@Transactional
	public void Delete(Long id) {
		addressDao.deleteById(id);
	}

	@Override
	@Transactional
	public Page<Address> findAll(Pageable page) {
		// TODO Auto-generated method stub
		return null;
	}

}
