package com.projekat.Procesi.handler;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InitReferent implements TaskListener {

	@Autowired
	IdentityService identityService;
	
	@Override
	public void notify(DelegateTask delegateTask) {
		String referentID = (String)delegateTask.getAssignee();
		User referent = identityService.createUserQuery().userId(referentID).singleResult();
		delegateTask.getExecution().setVariable("referent", referent);
	}

}
