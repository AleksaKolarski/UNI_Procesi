package com.projekat.Procesi.controller;

import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.engine.AuthorizationService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.authorization.Authorization;
import org.camunda.bpm.engine.authorization.Permissions;
import org.camunda.bpm.engine.authorization.Resources;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
	AuthorizationService authorizationService;
	
	@Autowired
	Util util;
	
	
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
	
	
	@PostMapping("/initUsers")
	public ResponseEntity<String> initUsers(){
		List<User> users = identityService.createUserQuery().userIdIn("student1").list();
		if (users.isEmpty()) {
			// GROUPS
			Group groupStudents = identityService.newGroup("students");
			groupStudents.setName("Studenti");
			groupStudents.setType("WORKFLOW");
			identityService.saveGroup(groupStudents);

			Group groupProfessors = identityService.newGroup("professors");
			groupProfessors.setName("Profesori");
			groupProfessors.setType("WORKFLOW");
			identityService.saveGroup(groupProfessors);

			Group groupStudentService = identityService.newGroup("studentService");
			groupStudentService.setName("Studentska sluzba");
			groupStudentService.setType("WORKFLOW");
			identityService.saveGroup(groupStudentService);

			// USERS
			User userStudent1 = identityService.newUser("student1");
			userStudent1.setFirstName("Student1");
			userStudent1.setLastName("Student1");
			userStudent1.setEmail("aleksa.kolarski96@gmail.com");
			userStudent1.setPassword("student1");
			identityService.saveUser(userStudent1);

			User userStudent2 = identityService.newUser("student2");
			userStudent2.setFirstName("Student2");
			userStudent2.setLastName("Student2");
			userStudent2.setEmail("aleksa.kolarski96@gmail.com");
			userStudent2.setPassword("student2");
			identityService.saveUser(userStudent2);

			User userProfessor1 = identityService.newUser("professor1");
			userProfessor1.setFirstName("Profesor1");
			userProfessor1.setLastName("Profesor1");
			userProfessor1.setEmail("aleksa.kolarski96@gmail.com");
			userProfessor1.setPassword("professor1");
			identityService.saveUser(userProfessor1);

			User userProfessor2 = identityService.newUser("professor2");
			userProfessor2.setFirstName("Profesor2");
			userProfessor2.setLastName("Profesor2");
			userProfessor2.setEmail("aleksa.kolarski96@gmail.com");
			userProfessor2.setPassword("professor2");
			identityService.saveUser(userProfessor2);

			User userProfessor3 = identityService.newUser("professor3");
			userProfessor3.setFirstName("Profesor3");
			userProfessor3.setLastName("Profesor3");
			userProfessor3.setEmail("aleksa.kolarski96@gmail.com");
			userProfessor3.setPassword("professor3");
			identityService.saveUser(userProfessor3);

			User userProfessor4 = identityService.newUser("professor4");
			userProfessor4.setFirstName("Profesor4");
			userProfessor4.setLastName("Profesor4");
			userProfessor4.setEmail("aleksa.kolarski96@gmail.com");
			userProfessor4.setPassword("professor4");
			identityService.saveUser(userProfessor4);

			User userProfessor5 = identityService.newUser("professor5");
			userProfessor5.setFirstName("Profesor5");
			userProfessor5.setLastName("Profesor5");
			userProfessor5.setEmail("aleksa.kolarski96@gmail.com");
			userProfessor5.setPassword("professor5");
			identityService.saveUser(userProfessor5);

			User userProfessor6 = identityService.newUser("professor6");
			userProfessor6.setFirstName("Profesor6");
			userProfessor6.setLastName("Profesor6");
			userProfessor6.setEmail("aleksa.kolarski96@gmail.com");
			userProfessor6.setPassword("professor6");
			identityService.saveUser(userProfessor6);

			User userDean = identityService.newUser("dean");
			userDean.setFirstName("Dean");
			userDean.setLastName("Dean");
			userDean.setEmail("aleksa.kolarski96@gmail.com");
			userDean.setPassword("dean");
			identityService.saveUser(userDean);

			User userStudentService1 = identityService.newUser("service1");
			userStudentService1.setFirstName("Service1");
			userStudentService1.setLastName("Service1");
			userStudentService1.setEmail("aleksa.kolarski96@gmail.com");
			userStudentService1.setPassword("service1");
			identityService.saveUser(userStudentService1);

			User userStudentService2 = identityService.newUser("service2");
			userStudentService2.setFirstName("Service2");
			userStudentService2.setLastName("Service2");
			userStudentService2.setEmail("aleksa.kolarski96@gmail.com");
			userStudentService2.setPassword("service2");
			identityService.saveUser(userStudentService2);

			User userStudentService3 = identityService.newUser("service3");
			userStudentService3.setFirstName("Service3");
			userStudentService3.setLastName("Service3");
			userStudentService3.setEmail("aleksa.kolarski96@gmail.com");
			userStudentService3.setPassword("service3");
			identityService.saveUser(userStudentService3);
			
			User userLibrary = identityService.newUser("library");
			userLibrary.setFirstName("Library");
			userLibrary.setLastName("Library");
			userLibrary.setEmail("aleksa.kolarski96@gmail.com");
			userLibrary.setPassword("library");
			identityService.saveUser(userLibrary);

			// ASSIGNING GROUPS
			identityService.createMembership("student1", "students");
			identityService.createMembership("student2", "students");
			identityService.createMembership("professor1", "professors");
			identityService.createMembership("professor2", "professors");
			identityService.createMembership("professor3", "professors");
			identityService.createMembership("professor4", "professors");
			identityService.createMembership("professor5", "professors");
			identityService.createMembership("professor6", "professors");
			identityService.createMembership("service1", "studentService");
			identityService.createMembership("service2", "studentService");
			identityService.createMembership("service3", "studentService");

			// GROUP AUTHORIZATIONS
			Authorization studentsTasklistAuth = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
			studentsTasklistAuth.setGroupId("students");
			studentsTasklistAuth.addPermission(Permissions.ACCESS);
			studentsTasklistAuth.setResourceId("*");
			studentsTasklistAuth.setResource(Resources.APPLICATION);
			authorizationService.saveAuthorization(studentsTasklistAuth);
			Authorization studentsAuthAuth = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
			studentsAuthAuth.setGroupId("students");
			studentsAuthAuth.addPermission(Permissions.ALL);
			studentsAuthAuth.setResourceId("WORKFLOW");
			studentsAuthAuth.setResource(Resources.AUTHORIZATION);
			authorizationService.saveAuthorization(studentsAuthAuth);

			Authorization professorsTasklistAuth = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
			professorsTasklistAuth.setGroupId("professors");
			professorsTasklistAuth.addPermission(Permissions.ACCESS);
			professorsTasklistAuth.setResourceId("*");
			professorsTasklistAuth.setResource(Resources.APPLICATION);
			authorizationService.saveAuthorization(professorsTasklistAuth);
			Authorization professorsAuthAuth = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
			professorsAuthAuth.setGroupId("professors");
			professorsAuthAuth.addPermission(Permissions.ALL);
			professorsAuthAuth.setResourceId("WORKFLOW");
			professorsAuthAuth.setResource(Resources.AUTHORIZATION);
			authorizationService.saveAuthorization(professorsAuthAuth);

			Authorization studentServiceTasklistAuth = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
			studentServiceTasklistAuth.setGroupId("studentService");
			studentServiceTasklistAuth.addPermission(Permissions.ACCESS);
			studentServiceTasklistAuth.setResourceId("*");
			studentServiceTasklistAuth.setResource(Resources.APPLICATION);
			authorizationService.saveAuthorization(studentServiceTasklistAuth);
			Authorization studentServiceAuthAuth = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
			studentServiceAuthAuth.setGroupId("studentService");
			studentServiceAuthAuth.addPermission(Permissions.ALL);
			studentServiceAuthAuth.setResourceId("WORKFLOW");
			studentServiceAuthAuth.setResource(Resources.AUTHORIZATION);
			authorizationService.saveAuthorization(studentServiceAuthAuth);
			
			// USER AUTHORIZATIONS
			Authorization libraryTasklistAuth = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
			libraryTasklistAuth.setUserId(userLibrary.getId());
			libraryTasklistAuth.addPermission(Permissions.ACCESS);
			libraryTasklistAuth.setResourceId("*");
			libraryTasklistAuth.setResource(Resources.APPLICATION);
			authorizationService.saveAuthorization(libraryTasklistAuth);
			Authorization libraryAuthAuth = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
			libraryAuthAuth.setUserId(userLibrary.getId());
			libraryAuthAuth.addPermission(Permissions.ALL);
			libraryAuthAuth.setResourceId("WORKFLOW");
			libraryAuthAuth.setResource(Resources.AUTHORIZATION);
			authorizationService.saveAuthorization(libraryAuthAuth);
		}
		return new ResponseEntity<>("Korisnici inicijalizovani", HttpStatus.OK);
	}
	
}
