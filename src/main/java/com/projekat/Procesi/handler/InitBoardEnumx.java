package com.projekat.Procesi.handler;

import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.impl.form.type.EnumFormType;
import org.camunda.bpm.engine.impl.form.type.StringFormType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InitBoardEnumx implements TaskListener {

	@Autowired
	FormService formService;
	
	@Autowired
	IdentityService identityService;
	
	@Override
	public void notify(DelegateTask delegateTask) {
		String taskId = delegateTask.getId();
		TaskFormData tfd = formService.getTaskFormData(taskId);
		List<FormField> fields = tfd.getFormFields();
		List<User> professors = identityService.createUserQuery().memberOfGroup("professors").list();
		User mentor = (User) delegateTask.getExecution().getVariable("mentor");
		for (FormField f : fields) {
			if(f.getTypeName().equals("enum")) {
				if(f.getId().equals("inPresidentx") || f.getId().equals("inBoard1x") || f.getId().equals("inBoard2x") || f.getId().equals("inBoard3x")) {
					EnumFormType enumFormType = (EnumFormType) f.getType();
					Map<String, String> values = enumFormType.getValues();
					if(f.getId().equals("inBoard2x") || f.getId().equals("inBoard3x")) {
						values.put("", "");
					}
					for(User professor: professors) {
						if(professor.getId().equals(mentor.getId())) {
							continue;
						}
						values.put(professor.getId(), professor.getFirstName() + " " + professor.getLastName());
					}
				}
			}
		}
	}

}
