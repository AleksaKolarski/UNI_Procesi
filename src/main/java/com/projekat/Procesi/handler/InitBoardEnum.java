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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InitBoardEnum implements TaskListener {

	@Autowired
	FormService formService;
	
	@Autowired
	IdentityService identityService;
	
	@Override
	public void notify(DelegateTask delegateTask) {
		String taskId = delegateTask.getId();
		TaskFormData tfd = formService.getTaskFormData(taskId);
		List<FormField> fields = tfd.getFormFields();
		for (FormField f : fields) {
			if (f.getTypeName().equals("enum") && (f.getId().equals("inPredsednik") || f.getId().equals("inClan1") || f.getId().equals("inClan2") || f.getId().equals("inClan3"))) {
				EnumFormType enumFormType = (EnumFormType) f.getType();
				Map<String, String> values = enumFormType.getValues();
				List<User> professors = identityService.createUserQuery().memberOfGroup("professors").list();
				User mentor = (User) delegateTask.getExecution().getVariable("mentor");
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
