package com.grupo7.oo2spring.handlers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

	@Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception)
                                        throws IOException, ServletException {
        String mensajeError = "Error desconocido";

        if (exception instanceof BadCredentialsException) {
        	mensajeError = "Usuario o contraseña incorrectos";
        } else if (exception instanceof UsernameNotFoundException) {
        	mensajeError = "Usuario no encontrado";
        } else if (exception instanceof LockedException) {
        	mensajeError = "La cuenta está bloqueada";
        } else if (exception instanceof DisabledException) {
        	mensajeError = "La cuenta está deshabilitada";
        } else {
            mensajeError = "Error de autenticación desconocido: " + exception.getMessage();
        }

        request.setAttribute("error_message", mensajeError);
        request.getRequestDispatcher("/usuario/error_login").forward(request, response);
    }
}