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
public class InitBoardx implements ExecutionListener {
	
	@Autowired
	IdentityService identityService;

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		
		List<User> boardList = new ArrayList<User>();
		List<User> boardListNoMentor = new ArrayList<>();
		
		boardList.add((User)execution.getVariable("mentor"));
		
		String presidentID = (String)execution.getVariable("inPresidentx");
		User president = identityService.createUserQuery().userId(presidentID).singleResult();
		execution.setVariable("president", president);
		boardList.add(president);
		boardListNoMentor.add(president);
		
		String board1ID = (String)execution.getVariable("inBoard1x");
		User board1 = identityService.createUserQuery().userId(board1ID).singleResult();
		execution.setVariable("board1", board1);
		boardList.add(board1);
		boardListNoMentor.add(board1);	
		
		String board2ID = (String)execution.getVariable("inBoard2x");
		if(board2ID != null && !board2ID.equals("")) {
			User board2 = identityService.createUserQuery().userId(board2ID).singleResult();
			execution.setVariable("board2", board2);
			boardList.add(board2);
			boardListNoMentor.add(board2);
		}
		else {
			execution.setVariable("board2", new UserDTO("", "", ""));
		}
		
		String board3ID = (String)execution.getVariable("inBoard3x");
		if(board3ID != null && !board3ID.equals("")) {
			User board3 = identityService.createUserQuery().userId(board3ID).singleResult();
			execution.setVariable("board3", board3);
			boardList.add(board3);
			boardListNoMentor.add(board3);
		}
		else {
			execution.setVariable("board3", new UserDTO("", "", ""));
		}
		
		execution.setVariable("boardList", boardList);
		execution.setVariable("boardListNoMentor", boardListNoMentor);
	}

}
