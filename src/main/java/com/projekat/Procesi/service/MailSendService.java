package com.projekat.Procesi.service;


import javax.mail.internet.MimeMessage;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.projekat.Procesi.storage.FileSystemStorageService;

import org.camunda.bpm.engine.delegate.Expression;

@Service
public class MailSendService implements JavaDelegate {
	
	private Expression to;
	private Expression from;
	private Expression messageTxt;
	private Expression subject;
	private Expression attachmentName;
	
	@Autowired
    private JavaMailSender emailSender;
	
	@Autowired
	FileSystemStorageService storageService;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		MimeMessage message = emailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setTo((String)to.getValue(execution));
			helper.setSubject((String)subject.getValue(execution));
			helper.setText((String)messageTxt.getValue(execution));
			
			String attachmentFileName = (String)attachmentName.getValue(execution);
			Resource resourceAtt = storageService.loadAsResource(attachmentFileName);
			helper.addAttachment(attachmentFileName, resourceAtt.getFile());
			
		}
		catch (Exception e) {
		}
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

	public Expression getAttachmentName() {
		return attachmentName;
	}

	public void setAttachmentName(Expression attachmentName) {
		this.attachmentName = attachmentName;
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
