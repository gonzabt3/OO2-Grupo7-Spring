package com.grupo7.oo2spring.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
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
	
	@ExceptionHandler(UsuarioNoEncontradoException.class)
    public String manejaExcepcionEmpleado(UsuarioNoEncontradoException ex, Model model, HttpServletRequest request) {
        ex.printStackTrace();

        model.addAttribute("status", HttpStatus.CONFLICT.value()); // Código 403
        model.addAttribute("error", "Usuario No Encontrado");
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("path", request.getRequestURI());

        return "error/404_Usuario";
    }
	
	@ExceptionHandler(UsuarioEsEmpleadoException.class)
    public String manejaExcepcionUsuario(UsuarioEsEmpleadoException ex, Model model, HttpServletRequest request) {
        ex.printStackTrace();

        model.addAttribute("status", HttpStatus.NOT_FOUND.value()); // Código 403
        model.addAttribute("error", "Usuario Ya es Empledo");
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("path", request.getRequestURI());

        return "error/Conflicto_Usuario";
    }


	public ModelAndView manejarExcepcionGenerica(Exception ex) {
	    ModelAndView mav = new ModelAndView("error");
	    mav.addObject("mensaje", "Ocurrió un error inesperado. Por favor, intentá más tarde.");
	    return mav;
	}
}
