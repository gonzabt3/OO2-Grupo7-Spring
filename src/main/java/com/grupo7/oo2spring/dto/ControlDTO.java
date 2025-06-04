package com.grupo7.oo2spring.dto;

import java.time.LocalDate;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ControlDTO {

    private int idControl;
    private TicketDTO ticket;
    private EmpleadoDTO empleado;
    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;
    private boolean finalizado;

    // Getters y setters
    public int getIdControl() {
        return idControl;
    }

    public void setIdControl(int idControl) {
        this.idControl = idControl;
    }

    public EmpleadoDTO getEmpleadoDTO() {
		return empleado;
	}

	public void setEmpleadoDTO(EmpleadoDTO empleado) {
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
}