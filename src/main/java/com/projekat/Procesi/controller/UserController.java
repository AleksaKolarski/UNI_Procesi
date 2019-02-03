package com.projekat.Procesi.controller;

import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.projekat.Procesi.dto.UserDTO;
import com.projekat.Procesi.util.Util;

@RestController
@RequestMapping("/projekat/user")
public class UserController {
		
	@Autowired
	private Util util;
	
	@GetMapping("/currentUser")
	public ResponseEntity<UserDTO> getCurrentUser(){
		
		User user;
		UserDTO userDTO;
		
		user = util.getCurrentUser();
		if(user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		userDTO = UserDTO.fromUser(user);
		
		Group group = util.getCurrentUserGroup();
		
		if(group != null) {
			userDTO.setGroupId(group.getId());
		}

		return new ResponseEntity<>(userDTO, HttpStatus.OK);
	}
	
}
