package com.projekat.Procesi.handler;

import java.util.List;

import org.camunda.bpm.engine.AuthorizationService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
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
		
		List<User> students = identityService.createUserQuery().memberOfGroup("students").list();
		List<User> professors = identityService.createUserQuery().memberOfGroup("professors").list();
		List<User> referents = identityService.createUserQuery().memberOfGroup("studentService").list();
		User dean = identityService.createUserQuery().userId("dean").singleResult();
		User library = identityService.createUserQuery().userId("library").singleResult();
		
		execution.setVariable("students", students);
		execution.setVariable("professors", professors);
		execution.setVariable("referents", referents);
		execution.setVariable("dean", dean);
		execution.setVariable("library", library);
	}

}
