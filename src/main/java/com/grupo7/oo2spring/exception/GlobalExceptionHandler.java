package com.grupo7.oo2spring.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(TicketNoEncontradoException.class)
	public ModelAndView manejarHtml(TicketNoEncontradoException ex) {
	    ModelAndView mav = new ModelAndView("/ticket/error_ticket");
	    mav.addObject("mensaje", ex.getMessage());
	    return mav;
	}

	public ModelAndView manejarExcepcionGenerica(Exception ex) {
	    ModelAndView mav = new ModelAndView("error");
	    mav.addObject("mensaje", "Ocurrió un error inesperado. Por favor, intentá más tarde.");
	    return mav;
	}
}
