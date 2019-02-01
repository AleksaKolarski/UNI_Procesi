package com.projekat.Procesi.controller;

import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.rest.dto.identity.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.projekat.Procesi.util.Util;

@RestController
@RequestMapping("/projekat/user")
public class UserController {
		
	@Autowired
	private Util util;
	
	@GetMapping("/getCurrentUser")
	public ResponseEntity<UserDto> getCurrentUser(){
		
		User user;
		UserDto userDTO;
		
		user = util.getCurrentUser();
		if(user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		userDTO = UserDto.fromUser(user, false);

		return new ResponseEntity<>(userDTO, HttpStatus.OK);
	}
	
}
