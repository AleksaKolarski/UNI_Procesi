package com.projekat.Procesi.controller;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/projekat/process")
public class ProcessController {
	
	private String processName = "Process_1";
	
	@Autowired
	private RuntimeService runtimeService;
	
	@GetMapping("/start")
	public ResponseEntity<String> startProcess(){
		ProcessInstance processInstance;
		try {
			processInstance = runtimeService.startProcessInstanceByKey(processName);
			return new ResponseEntity<>(processInstance.getId(), HttpStatus.OK);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Could not start process: " + processName, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
