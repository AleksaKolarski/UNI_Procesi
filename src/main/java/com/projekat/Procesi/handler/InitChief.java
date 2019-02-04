package com.projekat.Procesi.handler;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InitChief implements ExecutionListener {
	
	@Autowired
	IdentityService identityService;

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		String chiefID = (String)execution.getVariable("inRukovodilac");
		User chief = identityService.createUserQuery().userId(chiefID).singleResult();
		execution.setVariable("chief", chief);
	}

}
