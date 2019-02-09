package com.projekat.Procesi.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import org.camunda.bpm.engine.delegate.Expression;

@Service
public class MailSendService implements JavaDelegate {
	
	private Expression to;
	private Expression from;
	private Expression messageTxt;
	private Expression subject;
	
	@Autowired
    private JavaMailSender emailSender;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(execution.getVariable("testText"));
		SimpleMailMessage message = new SimpleMailMessage(); 
        message.setTo((String)to.getValue(execution)); 
        message.setSubject((String)subject.getValue(execution)); 
        message.setText((String)messageTxt.getValue(execution));
        
        emailSender.send(message);
	}

	

	public Expression getTo() {
		return to;
	}



	public void setTo(Expression to) {
		this.to = to;
	}



	public Expression getFrom() {
		return from;
	}



	public void setFrom(Expression from) {
		this.from = from;
	}



	public Expression getMessageTxt() {
		return messageTxt;
	}



	public void setMessageTxt(Expression messageTxt) {
		this.messageTxt = messageTxt;
	}



	public Expression getSubject() {
		return subject;
	}



	public void setSubject(Expression subject) {
		this.subject = subject;
	}



	public JavaMailSender getEmailSender() {
		return emailSender;
	}



	public void setEmailSender(JavaMailSender emailSender) {
		this.emailSender = emailSender;
	}



	public JavaMailSender getSender() {
		return emailSender;
	}

	public void setSender(JavaMailSender sender) {
		this.emailSender = sender;
	}
	
	

}
