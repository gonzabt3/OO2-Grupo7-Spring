package com.grupo7.oo2spring.exception;

public class TicketCreacionException extends RuntimeException {
    public TicketCreacionException(String mensaje) {
        super(mensaje);
    }
}