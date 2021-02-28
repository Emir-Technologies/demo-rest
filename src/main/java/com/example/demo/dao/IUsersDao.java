package com.example.demo.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.entity.Users;

public interface IUsersDao extends CrudRepository<Users, Long>{

	//JPA
	public Users findByusername(String username);
	
	//JQL
/*	@Query("select u from User u where  u.username = ?")
	public Users findByusername2(String username);
*/
}
