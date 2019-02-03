package com.projekat.Procesi.controller;

import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.projekat.Procesi.dto.EnumDTO;
import com.projekat.Procesi.dto.UserDTO;
import com.projekat.Procesi.util.Util;

@RestController
@RequestMapping("/projekat/user")
public class UserController {
	
	@Autowired
	IdentityService identityService;
	
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
	
	@GetMapping("/professors")
	public ResponseEntity<List<EnumDTO>> getProfessors(){
		
		List<EnumDTO> enumDTOList = new ArrayList<>();
		List<User> professors = identityService.createUserQuery().memberOfGroup("professors").list();
		for(User user: professors) {
			enumDTOList.add(new EnumDTO(user.getId(), user.getFirstName() + " " + user.getLastName()));
		}
		return new ResponseEntity<List<EnumDTO>>(enumDTOList, HttpStatus.OK);
	}
	
}
