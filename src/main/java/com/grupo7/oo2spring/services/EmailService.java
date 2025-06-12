package com.grupo7.oo2spring.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

import org.springframework.mail.MailException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

	    private final JavaMailSender mailSender;
	    
	    @Value("${EMAIL_USERNAME}") // <-- Dirección de Correo (ej. tunombre@gmail.com)
	    private String emailUsername; 
	    
	    @Value("${EMAIL_PASSWORD}") // <-- Api key generado (ej. gmail)
	    private String emailPassword; // 
	    
	    // Dirección de remitente que usarás, EMAIL_USERNAME de Gmail
	    @Value("${EMAIL_TEST}") 
	    private String emailSenderFrom;
	 
	    @PostConstruct
	    public void checkEmailConfig() {
	        System.out.println("--- Configuración de EmailService ---");
	        System.out.println("EMAIL_USERNAME cargado (Gmail): '" + emailUsername);
	        //La contraseña real por seguridad no se imprime, pero verifica que no esté vacía.
	        System.out.println("EMAIL_PASSWORD cargado: '" + (emailPassword != null && !emailPassword.isEmpty() ? "******" : "No cargada o vacía") + "'"); 
	        System.out.println("EMAIL_SENDER_FROM cargado: '" + emailSenderFrom + "'"); // Comillas para ver espacios
	        System.out.println("JavaMailSender bean: " + (mailSender != null ? "Inicializado" : "NO INICIALIZADO"));
	        System.out.println("-----------------------------------");
	    }
	 public void enviarEmail(String receptor, String asunto, String cuerpo) {
	        SimpleMailMessage mensaje = new SimpleMailMessage();
        	mensaje.setTo(receptor);
        	mensaje.setFrom(emailSenderFrom);
	        mensaje.setSubject(asunto);
	        mensaje.setText(cuerpo);
	        try {
	            mailSender.send(mensaje);
	            System.out.println("Mensaje enviado a " + receptor);
	        } catch (MailException e) {
	            System.err.println("ERROR: Fallo al enviar email a " + receptor + ": " + e.getMessage());
	            throw new RuntimeException("Fallo al enviar email", e);
	        }
	        
	 }
}
