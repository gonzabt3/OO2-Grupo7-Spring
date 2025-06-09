package com.grupo7.oo2spring.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

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
