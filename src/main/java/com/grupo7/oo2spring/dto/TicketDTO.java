package com.grupo7.oo2spring.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.grupo7.oo2spring.models.Ticket;
import com.grupo7.oo2spring.models.Usuario;
import com.grupo7.oo2spring.models.Area;
import com.grupo7.oo2spring.models.Prioridad;
import com.grupo7.oo2spring.models.Estado;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class TicketDTO {

    private int idTicket;

    private String titulo;

    private String descripcion;

    private LocalDate fechaCreacion;

    private LocalDate fechaCierre;

    private Usuario usuarioCreador;

    private Estado estado;

    private Prioridad prioridad;
    
    private Area area;

    private List<ControlDTO> procesos = new ArrayList<>();
    
   
    public TicketDTO(String titulo, String descripcion,
			Usuario usuarioCreador) {
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.fechaCreacion = LocalDate.now();
		this.fechaCierre = null;
		this.usuarioCreador = usuarioCreador;
		this.estado = Estado.PENDIENTE;
		this.prioridad = Prioridad.SIN_ASIGNAR;
		this.area = Area.SIN_ASIGNAR;
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
    

    public Usuario getUsuarioCreador() {
		return usuarioCreador;
	}

	public void setUsuarioCreador(Usuario usuarioCreador) {
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

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}
}