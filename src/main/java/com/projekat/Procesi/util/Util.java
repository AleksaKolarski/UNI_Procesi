package com.projekat.Procesi.util;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.impl.identity.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Util {
	
	@Autowired
	private IdentityService identityService;
	
	public User getCurrentUser() {
		
		Authentication authentication;
		String userId;
		User user;
		
		authentication = identityService.getCurrentAuthentication();
		if(authentication == null) {
			return null;
		}
		
		userId = authentication.getUserId();
		
		user = identityService.createUserQuery().userId(userId).singleResult();
		if(user == null) {
			return null;
		}
		
		return user;
	}
	
	public Group getCurrentUserGroup() {
		
		User user = getCurrentUser();
		if(user == null) {
			return null;
		}
		return identityService.createGroupQuery().groupMember(user.getId()).singleResult();
	}
}
