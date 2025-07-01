package com.grupo7.oo2spring.models;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
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
    
    @Enumerated(EnumType.STRING)
    private Area area;
    


    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Control> procesos;
    
    public void addControl(Control control) {
        if (!this.procesos.contains(control)) {
            this.procesos.add(control);
            control.setTicket(this); // Establece el Ticket en el Control, que es el lado dueño
        }
    }
        
    	public Ticket(String titulo, String descripcion,
    			Usuario usuarioCreador) {
    		this.titulo = titulo;
    		this.descripcion = descripcion;
    		this.fechaCreacion = LocalDate.now();
    		this.fechaCierre = null;
    		this.usuarioCreador = usuarioCreador;
    		this.estado = Estado.ABIERTO;
    		this.prioridad = Prioridad.SIN_ASIGNAR;
    		this.area = Area.SIN_ASIGNAR;
    		this.procesos = new ArrayList<Control>();
    	}
        
        
 
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

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

}