package com.projekat.Procesi.handler;

import org.camunda.bpm.engine.AuthorizationService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StartProcessHandler implements ExecutionListener {

	@Autowired
	IdentityService identityService;
	
	@Autowired
	AuthorizationService authorizationService;
	
	
	@Override
	public void notify(DelegateExecution execution) throws Exception {
		
		System.out.println("Zapocet proces");
		
		// korisnici: vise studenata, rukovodilacPrograma, predmetniProfesor, vise clanova komisije (profesora), dekan, vise radnika u studentskoj sluzbi(referenti)
		// grupe: studenti, profesori, studentskaSluzba
		
		
		// GROUPS
		Group groupStudents = identityService.newGroup("students");
		groupStudents.setName("Studenti");
		groupStudents.setType("WORKFLOW");
		identityService.saveGroup(groupStudents);
		
		Group groupProfessors = identityService.newGroup("professors");
		groupStudents.setName("Profesori");
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
		userStudent1.setEmail("student1@gmail.com");
		userStudent1.setPassword("student1");
		identityService.saveUser(userStudent1);
		
		User userStudent2 = identityService.newUser("student2");
		userStudent2.setFirstName("Student2");
		userStudent2.setLastName("Student2");
		userStudent2.setEmail("student2@gmail.com");
		userStudent2.setPassword("student2");
		identityService.saveUser(userStudent2);
		
		User userProfessor1 = identityService.newUser("professor1");
		userProfessor1.setFirstName("Profesor1");
		userProfessor1.setLastName("Profesor1");
		userProfessor1.setEmail("profesor1@gmail.com");
		userProfessor1.setPassword("profesor1");
		identityService.saveUser(userProfessor1);
		
		User userProfessor2 = identityService.newUser("professor2");
		userProfessor2.setFirstName("Profesor2");
		userProfessor2.setLastName("Profesor2");
		userProfessor2.setEmail("profesor2@gmail.com");
		userProfessor2.setPassword("profesor2");
		identityService.saveUser(userProfessor2);
		
		User userProfessor3 = identityService.newUser("professor3");
		userProfessor3.setFirstName("Profesor3");
		userProfessor3.setLastName("Profesor3");
		userProfessor3.setEmail("profesor3@gmail.com");
		userProfessor3.setPassword("profesor3");
		identityService.saveUser(userProfessor3);
		
		User userProfessor4 = identityService.newUser("professor4");
		userProfessor4.setFirstName("Profesor4");
		userProfessor4.setLastName("Profesor4");
		userProfessor4.setEmail("profesor4@gmail.com");
		userProfessor4.setPassword("profesor4");
		identityService.saveUser(userProfessor4);
		
		User userProfessor5 = identityService.newUser("professor5");
		userProfessor5.setFirstName("Profesor5");
		userProfessor5.setLastName("Profesor5");
		userProfessor5.setEmail("profesor5@gmail.com");
		userProfessor5.setPassword("profesor5");
		identityService.saveUser(userProfessor5);
		
		User userProfessor6 = identityService.newUser("professor6");
		userProfessor6.setFirstName("Profesor6");
		userProfessor6.setLastName("Profesor6");
		userProfessor6.setEmail("profesor6@gmail.com");
		userProfessor6.setPassword("profesor6");
		identityService.saveUser(userProfessor6);
		
		User userDean = identityService.newUser("dean");
		userDean.setFirstName("Dean");
		userDean.setLastName("Dean");
		userDean.setEmail("dean@gmail.com");
		userDean.setPassword("dean");
		identityService.saveUser(userDean);
		
		User userStudentService1 = identityService.newUser("service1");
		userStudentService1.setFirstName("Service1");
		userStudentService1.setLastName("Service1");
		userStudentService1.setEmail("service1@gmail.com");
		userStudentService1.setPassword("service1");
		identityService.saveUser(userStudentService1);
		
		User userStudentService2 = identityService.newUser("service2");
		userStudentService2.setFirstName("Service2");
		userStudentService2.setLastName("Service2");
		userStudentService2.setEmail("service2@gmail.com");
		userStudentService2.setPassword("service2");
		identityService.saveUser(userStudentService2);
		
		User userStudentService3 = identityService.newUser("service3");
		userStudentService3.setFirstName("Service3");
		userStudentService3.setLastName("Service3");
		userStudentService3.setEmail("service3@gmail.com");
		userStudentService3.setPassword("service3");
		identityService.saveUser(userStudentService3);
		
		
		
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
	}

}
