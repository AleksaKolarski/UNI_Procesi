package com.projekat.Procesi.handler;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaFormData;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaFormField;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaValue;

public class InitProfessorEnum implements ExecutionListener {

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		List<CamundaFormData> formDataList = execution.getBpmnModelElementInstance().getExtensionElements().getElementsQuery().filterByType(CamundaFormData.class).list();
		for(CamundaFormData formData: formDataList) {
			Collection<CamundaFormField> fields = formData.getCamundaFormFields();
			for (CamundaFormField field : fields) {
				if (field.getCamundaType().equals("enum")) {
					Map<String, String> enumValues = new HashMap<String, String>();
					String id = field.getCamundaId();
					Collection<CamundaValue> values = field.getCamundaValues();
					
					for (CamundaValue value : values) {
						enumValues.put(value.getCamundaId(), value.getCamundaName());
					}
					//Before that Serialize the enumValues to JSON
					execution.setVariable(id, enumValues);
				}
			}
		}
	}

}
