package com.grupo7.oo2spring.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

	    private final JavaMailSender mailSender;
	    
	    @Value("${EMAIL_USERNAME}") // <-- Dirección de Correo (ej. tunombre@gmail.com)
	    private String emailUsername; 
	    
	    @Value("${EMAIL_APIKEY}") // <-- Api key generado (ej. gmail)
	    private String emailAPIKey; // 
	    
	    // Dirección de remitente que usarás, EMAIL_USERNAME de Gmail
	    @Value("${EMAIL_TEST}") 
	    private String emailSenderFrom;
	    
	    private final TemplateEngine templateEngine;
	 
	    @PostConstruct
	    public void checkEmailConfig() {
	        System.out.println("--- Configuración de EmailService ---");
	        System.out.println("EMAIL_USERNAME cargado (Gmail): '" + emailUsername);
	        System.out.println("EMAIL_APIKEY cargado: '" + (emailAPIKey != null && !emailAPIKey.isEmpty() ? "******" : "No cargada o vacía") + "'"); 
	        System.out.println("EMAIL_SENDER_FROM cargado: '" + emailSenderFrom + "'");
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
	        	e.printStackTrace();
	            System.err.println("ERROR: Fallo al enviar email a " + receptor + ": " + e.getMessage());
	            throw new RuntimeException("Fallo al enviar email", e);
	        }
	        
	 }
	 
	 public void enviarEmailConHtml(String receptor, String asunto, String nombreTemplate, Map<String, Object> variables) {
		    try {
		        System.out.println("📨 Entró a enviarEmailConHtml()");
		        MimeMessage mimeMessage = mailSender.createMimeMessage();
		        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");

		        Context context = new Context();
		        context.setVariables(variables);

		        String htmlContent = templateEngine.process("email-template", context);
		       

		        helper.setFrom(emailSenderFrom);
		        helper.setTo(receptor);
		        helper.setSubject(asunto);
		        helper.setText(htmlContent, true);
		        
		        System.out.println("🔍 nombreUsuario: " + variables.get("nombreUsuario"));
		        System.out.println("🔍 email: " + variables.get("email"));
		        System.out.println("🔍 mensaje: " + variables.get("mensaje"));

		        mailSender.send(mimeMessage);
		    } catch (MessagingException e) {
		        throw new RuntimeException("Error al enviar correo HTML", e);
		    } catch (Exception e) {
		    e.printStackTrace();
		    throw new RuntimeException("Error al enviar correo HTML", e);
		    }
		}
}