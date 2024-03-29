package com.projekat.Procesi.controller;

import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.repository.ProcessDefinition;
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
	
	String processName = "Projekat";
	
	@Autowired
	RuntimeService runtimeService;
	
	@Autowired
	IdentityService identityService;
	
	@Autowired
	RepositoryService repositoryService;
	
	@Autowired
	Util util;
	
	@GetMapping("/start")
	public ResponseEntity<String> startProcess(){
		
		User user;
		ProcessInstance processInstance;
		
		user = util.getCurrentUser();
		if(user == null) {
			return new ResponseEntity<>("You have to be logged in.", HttpStatus.UNAUTHORIZED);
		}
		
		List<ProcessInstance> processInstanceList = runtimeService.createProcessInstanceQuery().variableValueEquals("student", user.getId()).list();
		if(!processInstanceList.isEmpty()) {
			return new ResponseEntity<>("There is already one instance of this process started by user.", HttpStatus.BAD_REQUEST);
		}
		
		try {
			ProcessDefinition definition = repositoryService.createProcessDefinitionQuery().startableByUser(user.getId()).latestVersion().singleResult();
			processInstance = runtimeService.startProcessInstanceById(definition.getId());
			return new ResponseEntity<>(processInstance.getId(), HttpStatus.OK);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Could not start process: " + processName + "\nYou may not have rights to start it.", HttpStatus.INTERNAL_SERVER_ERROR);
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
	
	@GetMapping("/getByUser")
	public ResponseEntity<List<String>> getByUser(){
		
		User user = util.getCurrentUser();
		if(user == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		List<String> processIdList = new ArrayList<>();
		List<ProcessInstance> processInstanceList = runtimeService.createProcessInstanceQuery().variableValueEquals("student", user.getId()).list();
		for(ProcessInstance processInstace: processInstanceList) {
			processIdList.add(processInstace.getId());
		}
		return new ResponseEntity<List<String>>(processIdList, HttpStatus.OK);
	}
}
