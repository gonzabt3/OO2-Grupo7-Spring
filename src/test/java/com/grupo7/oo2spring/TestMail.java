/*package com.grupo7.oo2spring;

import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.grupo7.oo2spring.services.EmailService;

@SpringBootTest
public class TestMail {
	
	 @Autowired
	    private EmailService emailService;
	
	@Value("${EMAIL_USERNAME}")
	private String emailUsername;
	
	
	@Test
    public void testEnviarEmail() {
		emailService.enviarEmail(emailUsername, "Hola Mundo!", "Mail de ejemplo.");
	}
	
	}*/

