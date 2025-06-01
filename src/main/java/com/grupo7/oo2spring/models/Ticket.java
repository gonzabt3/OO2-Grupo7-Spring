package com.grupo7.oo2spring.models;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTicket;

    private String titulo;

    private String descripcion;

    private LocalDate fechaCreacion;

    private LocalDate fechaCierre;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuarioCreador;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    @Enumerated(EnumType.STRING)
    private Prioridad prioridad;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
    private List<Control> procesos;

    // Getters y setters
    public int getIdTicket() {
        return idTicket;
    }

    private void setIdTicket(int idTicket) {
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

    public List<Control> getProcesos() {
        return procesos;
    }

    public void setProcesos(List<Control> procesos) {
        this.procesos = procesos;
    }
}