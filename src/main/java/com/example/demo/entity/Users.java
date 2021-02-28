package com.example.demo.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class Users implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY	)
	@Column(name="id")
	private long id;
	

	@Column(name="username")
	private String username;
	
	@Column(name="password")
	private String password;
	
	@Column(name="enabled")
	private boolean enabled;
	
	/*
	 *The Annotation @ManyToMany create the relation table  
	 *automatically and add by default the foreign keys.
	 *   
	 *With the Annotation @JoinTable you can customize the 
	 *values like: Table name and Foreign keys names.
	 *
	 *The Unique Constrains makes that the values can't be
	 *repeated.
	 */
	
	@ManyToMany(
			fetch = FetchType.LAZY, 
			cascade = CascadeType.ALL)
	@JoinTable(
			name="usuarios_roles", 
			joinColumns= @JoinColumn(name="users_id"), 
			inverseJoinColumns = @JoinColumn(name="roles_id"))
	private List<Role> roles; 
	
	/*uniqueConstraints = {
	@UniqueConstraint(columnNames= {"users_id", "roles_id" })
	}
	*/
	/*@OneToOne
	private Profile profile;
	
	
	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}
*/
	public Users() {
		
	}
	
	public Users(String username, String password) {
		this.password = password;
		this.username = username;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}


	
	
}
