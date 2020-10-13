package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Address;

public interface IAddressDao extends JpaRepository<Address, Long>{

}
