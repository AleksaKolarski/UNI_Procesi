package com.projekat.Procesi.handler;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.identity.User;

public class ClanOdbijaPrisustvo implements ExecutionListener {

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		Boolean potvrdjenoPrisustvo;
		try {
			potvrdjenoPrisustvo = (Boolean)execution.getVariable("inPotvrdjenoPrisustvo");
		}
		catch(Exception e) {
			potvrdjenoPrisustvo = true;
		}
		if(potvrdjenoPrisustvo == null) {
			potvrdjenoPrisustvo = true;
		}

		if (execution.getVariable("listaPrazna") == null) {
			execution.setVariable("listaPrazna", true);
		}
		System.out.println(potvrdjenoPrisustvo);
		if (potvrdjenoPrisustvo == false) {
			String lista = (String) execution.getVariable("listaOdbijenoPrisustvo");
			if (lista == null) {
				System.out.println("lista=null");
				lista = "";
			} else {
				lista += ", ";
			}
			User boardMember = (User) execution.getVariable("boardMember");
			lista += boardMember.getFirstName() + " " + boardMember.getLastName();
			execution.setVariable("listaOdbijenoPrisustvo", lista);
			execution.setVariable("listaPrazna", false);
			System.out.println(lista);
		}
	}

}
