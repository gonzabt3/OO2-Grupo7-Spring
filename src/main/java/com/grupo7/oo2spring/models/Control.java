package com.grupo7.oo2spring.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Control {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idControl;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;


    @ManyToOne
    @JoinColumn(name = "empleado_id")
    private Empleado empleado;

    private LocalDate fechaEntrada;

    private LocalDate fechaSalida;
    
    private String accion;

    private boolean finalizado;
    
    @Enumerated(EnumType.STRING)
    private Funcion funcion;
    
    
	public Control(Ticket ticket, Empleado empleado, LocalDate fechaEntrada, LocalDate fechaSalida,
			String accion, boolean finalizado, Funcion funcion) {
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

    private void setIdControl(int idControl) {
        this.idControl = idControl;
    }

    public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
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
	public Funcion getFuncion() {
		return funcion;
	}
	public void setFuncion(Funcion funcion) {
		this.funcion = funcion;
	}


}