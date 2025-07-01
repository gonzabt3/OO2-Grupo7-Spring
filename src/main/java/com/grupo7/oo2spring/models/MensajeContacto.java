package com.grupo7.oo2spring.models;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
public class MensajeContacto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idMensaje;

    private String mensaje;

    private LocalDateTime fechaEnvio;

    @ManyToOne
    private Usuario usuario;

	public MensajeContacto(String mensaje, Usuario usuario) {
		this.mensaje = mensaje;
		this.fechaEnvio = LocalDateTime.now();
		this.usuario = usuario;
	}
	
	@PrePersist
	public void prePersist() {
	    if (this.fechaEnvio == null) {
	        this.fechaEnvio = LocalDateTime.now();
	    }
	}

	public int getIdMensaje() {
		return idMensaje;
	}

	public void setIdMensaje(int idMensaje) {
		this.idMensaje = idMensaje;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public LocalDateTime getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(LocalDateTime fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return "MensajeContacto [idMensaje=" + idMensaje + ", mensaje=" + mensaje + ", fechaEnvio=" + fechaEnvio
				+ ", usuario=" + usuario + "]";
	}
    
	

}
