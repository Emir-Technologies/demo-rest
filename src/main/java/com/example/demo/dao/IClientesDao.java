package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Cliente;

public interface IClientesDao extends JpaRepository<Cliente, Long>{

}
