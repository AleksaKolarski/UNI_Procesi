package com.projekat.Procesi.handler;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InitStudent implements ExecutionListener {
	
	@Autowired
	IdentityService identityService;

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		// get variable $studentID
		// get user
		// set variable student
		String studentID = (String)execution.getVariable("studentID");
		User student = identityService.createUserQuery().userId(studentID).singleResult();
		execution.setVariable("student", student);
	}

}
