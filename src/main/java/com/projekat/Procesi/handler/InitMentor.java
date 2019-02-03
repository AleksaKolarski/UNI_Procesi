package com.projekat.Procesi.handler;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InitMentor implements ExecutionListener {

	@Autowired
	IdentityService identityService;
	
	@Override
	public void notify(DelegateExecution execution) throws Exception {
		String mentorID = (String)execution.getVariable("inMentor");
		User mentor = identityService.createUserQuery().userId(mentorID).singleResult();
		execution.setVariable("mentor", mentor);
	}

}
