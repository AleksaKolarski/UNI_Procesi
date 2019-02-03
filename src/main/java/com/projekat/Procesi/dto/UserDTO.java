package com.projekat.Procesi.dto;

import org.camunda.bpm.engine.identity.User;

public class UserDTO {
	
	private String id;
	private String firstname;
	private String lastname;
	private String email;
	private String groupId;
	
	
	public UserDTO() {}

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	public static UserDTO fromUser(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setFirstname(user.getFirstName());
		userDTO.setLastname(user.getLastName());
		userDTO.setEmail(user.getEmail());
		return userDTO;
	}
}
