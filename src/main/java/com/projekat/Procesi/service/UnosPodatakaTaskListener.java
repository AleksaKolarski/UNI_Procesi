package com.projekat.Procesi.service;

import java.util.Map;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;

public class UnosPodatakaTaskListener implements TaskListener {

	@Override
	public void notify(DelegateTask delegateTask) {
		System.out.println("PokrenutTask");
		Map<String, Object> mapa = delegateTask.getVariables();
		for(String key: mapa.keySet()) {
			System.out.println(key);
		}
	}

}
