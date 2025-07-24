package com.grupo7.oo2spring.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.grupo7.oo2spring.models.Empleado;
import com.grupo7.oo2spring.models.Funcion;
import com.grupo7.oo2spring.models.Ticket;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ControlDTO {

    private int idControl;
    private Ticket ticket;
    private String empleado;
    private LocalDateTime fechaEntrada;
    private LocalDateTime fechaSalida;
    private String accion;
    private boolean finalizado;
    private Funcion funcion;
    private String tituloTicket;

    public ControlDTO(int idControl,Ticket ticket, String empleado, LocalDateTime fechaEntrada,
			LocalDateTime fechaSalida, String accion, boolean finalizado, Funcion funcion,String tituloTicket) {
		this.idControl = idControl;
    	this.ticket = ticket;
		this.empleado = empleado;
		this.fechaEntrada = fechaEntrada;
		this.fechaSalida = fechaSalida;
		this.accion = accion;
		this.finalizado = finalizado;
		this.funcion = funcion;
		this.tituloTicket=tituloTicket;
	}
    
    // Getters y setters
    public int getIdControl() {
        return idControl;
    }

    public void setIdControl(int idControl) {
        this.idControl = idControl;
    }

    public String getEmpleado() {
		return empleado;
	}

	public void setEmpleado(String empleado) {
		this.empleado = empleado;
	}

	public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
    
    public String getTituloTicket() {
    	return this.tituloTicket;
    }
    
    public void setTituloTicket(String titulo) {
    	this.tituloTicket=titulo;
    }

    public LocalDateTime getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(LocalDateTime fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public LocalDateTime getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDateTime fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public boolean isFinalizado() {
        return finalizado;
    }

    public void setFinalizado(boolean finalizado) {
        this.finalizado = finalizado;
    }

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	
}