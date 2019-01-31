package com.projekat.Procesi.controller;

import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.FormProperty;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.rest.dto.task.TaskDto;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/projekat/task")
public class TaskController {

	@Autowired
	TaskService taskService;
	
	@Autowired
	FormService formService;
	
	
	// GET ALL TASK BY PROCESS ID
	@GetMapping(value = "/getAll/{processInstanceId}")
	public ResponseEntity<List<TaskDto>> getByProcessInstanceId(@PathVariable("processInstanceId") String processInstanceId){
		
		List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
		List<TaskDto> tasksDTO = new ArrayList<>();
		for(Task task: tasks) {
			tasksDTO.add(TaskDto.fromEntity(task));
		}
		return new ResponseEntity<>(tasksDTO, HttpStatus.OK);
	}
	
	// GET TASK BY ID
	@GetMapping(value = "/{taskId}")
	public ResponseEntity<TaskDto> getTaskById(@PathVariable("taskId") String taskId){
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		if(task == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(TaskDto.fromEntity(task), HttpStatus.OK);
	}
	
	// GET TASK FORM BY ID
	@GetMapping(value = "/{taskId}/form")
	public ResponseEntity<List<FormField>> getTaskFormFields(@PathVariable("taskId") String taskId){
		
		TaskFormData taskFormData;
		try {
			taskFormData = formService.getTaskFormData(taskId);
		}
		catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		List<FormField> formFields = taskFormData.getFormFields();		
		return new ResponseEntity<>(formFields, HttpStatus.OK);
	}
	
	
}