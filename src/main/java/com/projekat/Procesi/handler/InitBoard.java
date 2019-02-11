package com.projekat.Procesi.handler;

import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projekat.Procesi.dto.UserDTO;

@Service
public class InitBoard implements ExecutionListener {
	
	@Autowired
	IdentityService identityService;

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		
		List<User> boardList = (List<User>) execution.getVariable("boardList"); 
		if(boardList != null) {
			boardList.clear();
		}
		else {
			boardList = new ArrayList<User>();
		}
		
		boardList.add((User)execution.getVariable("mentor"));
		
		String presidentID = (String)execution.getVariable("inPresident");
		User president = identityService.createUserQuery().userId(presidentID).singleResult();
		execution.setVariable("president", president);
		boardList.add(president);
		
		String board1ID = (String)execution.getVariable("inBoard1");
		User board1 = identityService.createUserQuery().userId(board1ID).singleResult();
		execution.setVariable("board1", board1);
		boardList.add(board1);
		
		String board2ID = (String)execution.getVariable("inBoard2");
		if(board2ID != null && !board2ID.equals("")) {
			User board2 = identityService.createUserQuery().userId(board2ID).singleResult();
			execution.setVariable("board2", board2);
			boardList.add(board2);
		}
		else {
			execution.setVariable("board2", new UserDTO("", "", ""));
		}
		
		String board3ID = (String)execution.getVariable("inBoard3");
		if(board3ID != null && !board3ID.equals("")) {
			User board3 = identityService.createUserQuery().userId(board3ID).singleResult();
			execution.setVariable("board3", board3);
			boardList.add(board3);
		}
		else {
			execution.setVariable("board3", new UserDTO("", "", ""));
		}
	}

}
