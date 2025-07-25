package com.grupo7.oo2spring.dto;

import com.grupo7.oo2spring.enums.TipoRol;
import com.grupo7.oo2spring.models.Empleado;
import com.grupo7.oo2spring.models.Usuario;
import com.grupo7.oo2spring.models.UsuarioBase;
import com.grupo7.oo2spring.models.Rol;

public class UsuarioDTO {
    private int id;
    private String dni;
    private String nombre;
    private String nombreUsuario;
    private String apellido;
	private String email;
    private Rol rol; 

    public UsuarioDTO(UsuarioBase u) {
        if (u instanceof Usuario) {
            //this.id = ((Usuario) u).getIdUsuario();
            this.rol = new Rol(TipoRol.USER);
            this.nombreUsuario = u.getNombreUsuario();
        } else if (u instanceof Empleado) {
        	 Empleado empleado = (Empleado) u;
            //this.id = ((Empleado) u).getIdEmpleado();
            this.rol = empleado.getRol();
            this.nombreUsuario = u.getNombreUsuario();
        }
        this.id = u.getId();
        this.nombre = u.getNombre();
        this.apellido = u.getApellido();
        this.dni = u.getDni();
        this.email = u.getEmail();
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}
	
    public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	
	

    
}

