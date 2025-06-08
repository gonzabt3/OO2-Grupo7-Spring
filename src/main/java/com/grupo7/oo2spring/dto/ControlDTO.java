package com.grupo7.oo2spring.dto;

import java.time.LocalDate;

import com.grupo7.oo2spring.models.Empleado;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ControlDTO {

    private int idControl;
    private TicketDTO ticket;
    private Empleado empleado;
    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;
    private String accion;
    private boolean finalizado;

    public ControlDTO(TicketDTO ticket, Empleado empleado, LocalDate fechaEntrada,
			LocalDate fechaSalida, String accion, boolean finalizado) {
		super();
		this.ticket = ticket;
		this.empleado = empleado;
		this.fechaEntrada = fechaEntrada;
		this.fechaSalida = fechaSalida;
		this.accion = accion;
		this.finalizado = finalizado;
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

    public LocalDate getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(LocalDate fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDate fechaSalida) {
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