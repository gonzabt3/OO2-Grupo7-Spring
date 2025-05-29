package grupo7ticketManager.models.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTicket;

    private String titulo;

    private String descripcion;

    private LocalDate fechaCreacion;

    private LocalDate fechaCierre;

    @ManyToOne
    @JoinColumn(name = "persona_creador_id")
    private Persona personaCreador;

    @ManyToOne
    @JoinColumn(name = "estado_id")
    private Estado estado;

    @Enumerated(EnumType.STRING)
    private Prioridad prioridad;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
    private List<Control> procesos;

    // Getters y setters
    public Integer getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(Integer idTicket) {
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

    public Persona getPersonaCreador() {
        return personaCreador;
    }

    public void setPersonaCreador(Persona personaCreador) {
        this.personaCreador = personaCreador;
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