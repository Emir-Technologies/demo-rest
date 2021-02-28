package com.example.demo.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.IUsersDao;
import com.example.demo.entity.Users;

@Service
public class UserService implements UserDetailsService {
	//Implement the defined interface UserDetailsService and 
	//its method UserDetails
	
	@Autowired
	private IUsersDao usersDao;
	
	
	private Logger logger  = LoggerFactory.getLogger(UserService.class);
			
	@Override
	@Transactional(readOnly=true)//Choose transacctional by Spring not Javax
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Users users = usersDao.findByusername(username); 
		
		//Lanza el error en caso de encontrar el usuario solicitado
		if(users == null) {
			logger.error("Error en el login: No existe el usuario '"+username+"' En el sistema!");
			throw new UsernameNotFoundException("Error en el login: No existe el usuario '"+username+"' En el sistema!");  
		}
		
		
		List<GrantedAuthority> authorities = users.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getRoleName()))
				.peek(authority -> logger.info("Role: "+ authority.getAuthority()))
				.collect(Collectors.toList());
						
		return  new User(users.getUsername(), users.getPassword(), users.isEnabled(), true, true, true, authorities);
		}

}
