package com.grupo7.oo2spring.dto;


import java.time.LocalDate;
import java.util.List;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TicketDTO {

    private int idTicket;

    private String titulo;

    private String descripcion;

    private LocalDate fechaCreacion;

    private LocalDate fechaCierre;

    private UsuarioDTO usuarioCreador;

    private Estado estado;

    private Prioridad prioridad;

    private List<ControlDTO> procesos;
    
   
	public TicketDTO(String titulo, String descripcion,
			UsuarioDTO usuarioCreador, Estado estado, Prioridad prioridad) {
		super();
		this.idTicket = idTicket;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.fechaCreacion = LocalDate.now();
		this.fechaCierre = null;
		this.usuarioCreador = usuarioCreador;
		this.estado = estado;
		this.prioridad = prioridad;
	}

    public int getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(int idTicket) {
        this.idTicket = idTicket;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDate getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(LocalDate fechaCierre) {
        this.fechaCierre = fechaCierre;
    }
    

    public UsuarioDTO getUsuarioCreador() {
		return usuarioCreador;
	}

	public void setUsuarioCreador(UsuarioDTO usuarioCreador) {
		this.usuarioCreador = usuarioCreador;
	}

	public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Prioridad getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
    }

    public List<ControlDTO> getProcesos() {
        return procesos;
    }

    public void setProcesos(List<ControlDTO> procesos) {
        this.procesos = procesos;
    }
    
}