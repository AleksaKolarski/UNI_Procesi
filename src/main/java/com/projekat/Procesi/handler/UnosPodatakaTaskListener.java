package com.projekat.Procesi.handler;

import java.util.Map;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;

@Service
public class UnosPodatakaTaskListener implements TaskListener {

	@Override
	public void notify(DelegateTask delegateTask) {
		System.out.println("Pokrenut task UnosPodataka");
		Map<String, Object> mapa = delegateTask.getVariables();
		for(String key: mapa.keySet()) {
			System.out.println(key);
		}
	}

}
