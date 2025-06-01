package com.grupo7.oo2spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	 @Autowired
	    private JavaMailSender mailSender;
	 
	 @Value("${EMAIL_TEST}")
		private String emailUsername;
	 
	 public void enviarEmail(String to, String subject, String body) {
		 
		 
	        SimpleMailMessage mensaje = new SimpleMailMessage();
	        mensaje.setTo(to);
	        mensaje.setSubject(subject);
	        mensaje.setText(body);
	        mensaje.setFrom(emailUsername);
	        mailSender.send(mensaje);
	        System.out.println("Mensaje enviado a " + to);
	 
}
}
