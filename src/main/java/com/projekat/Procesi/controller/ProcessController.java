package com.projekat.Procesi.controller;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.rest.dto.runtime.ProcessInstanceDto;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projekat.Procesi.util.Util;

@RestController
@RequestMapping("/projekat/process")
public class ProcessController {
	
	String processName = "Process_1";
	
	@Autowired
	RuntimeService runtimeService;
	
	@Autowired
	Util util;
	
	@GetMapping("/start")
	public ResponseEntity<String> startProcess(){
		
		User user;
		ProcessInstance processInstance;
		
		user = util.getCurrentUser();
		if(user == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		try {
			processInstance = runtimeService.startProcessInstanceByKey(processName);
			return new ResponseEntity<>(processInstance.getId(), HttpStatus.OK);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Could not start process: " + processName, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/{processInstanceId}")
	public ResponseEntity<ProcessInstanceDto> getById(@PathVariable("processInstanceId") String processInstanceId){
		
		ProcessInstance processInstance;
		ProcessInstanceDto processInstanceDTO;
		
		processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		if(processInstance == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		processInstanceDTO = ProcessInstanceDto.fromProcessInstance(processInstance);
		return new ResponseEntity<>(processInstanceDTO, HttpStatus.OK);
	}
}
