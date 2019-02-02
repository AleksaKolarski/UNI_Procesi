package com.projekat.Procesi.dto;

public class FormFieldSubmissionDTO {

	String fieldId;
	String fieldValue;
	String fieldType;
	
	
	public FormFieldSubmissionDTO() {}
	
	public FormFieldSubmissionDTO(String fieldId, String fieldValue, String fieldType) {
		super();
		this.fieldId = fieldId;
		this.fieldValue = fieldValue;
		this.fieldType = fieldType;
	}

	
	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
}
