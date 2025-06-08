package com.grupo7.oo2spring.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

	    private final JavaMailSender mailSender;
	 
	    @Value("${EMAIL_TEST}") //Este email va a enviar el email a receptor
		private String emailUsername;
	 
	 public void enviarEmail(String receptor, String asunto, String cuerpo) {
		 
		 
	        SimpleMailMessage mensaje = new SimpleMailMessage();
	        mensaje.setTo(receptor);
	        mensaje.setSubject(asunto);
	        mensaje.setText(cuerpo);
	        mensaje.setFrom(emailUsername);
	        mailSender.send(mensaje);
	        System.out.println("Mensaje enviado a " + receptor);
	 
}
}
