package com.grupo7.oo2spring.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.grupo7.oo2spring.models.Empleado;
import com.grupo7.oo2spring.models.Funcion;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ControlDTO {

    private int idControl;
    private TicketDTO ticket;
    private Empleado empleado;
    private LocalDateTime fechaEntrada;
    private LocalDateTime fechaSalida;
    private String accion;
    private boolean finalizado;
    private Funcion funcion;

    public ControlDTO(TicketDTO ticket, Empleado empleado, LocalDateTime fechaEntrada,
			LocalDateTime fechaSalida, String accion, boolean finalizado, Funcion funcion) {
		this.ticket = ticket;
		this.empleado = empleado;
		this.fechaEntrada = fechaEntrada;
		this.fechaSalida = fechaSalida;
		this.accion = accion;
		this.finalizado = finalizado;
		this.funcion = funcion;
	}
    
    // Getters y setters
    public int getIdControl() {
        return idControl;
    }

    public void setIdControl(int idControl) {
        this.idControl = idControl;
    }

    public Empleado getEmpleadoDTO() {
		return empleado;
	}

	public void setEmpleadoDTO(Empleado empleado) {
		this.empleado = empleado;
	}

	public TicketDTO getTicket() {
        return ticket;
    }

    public void setTicket(TicketDTO ticket) {
        this.ticket = ticket;
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