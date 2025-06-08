package com.grupo7.oo2spring.dto;

import com.grupo7.oo2spring.dto.Rol;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UsuarioDTO {
	
	 private int idUsuario;
	 
	 private String nombreUsuario;
	 
	  private String contraseña;
	  
	  private Rol rol;
	  
	  public UsuarioDTO(String nombre, String apellido, String dni, String email,
		       String nombreUsuario, String contraseña)  throws Exception {
			     validarNombreApellido(nombre, apellido);
			     validarNombreUsuario(nombreUsuario);
			     validarEmail(email);
			     validarDNI(dni);
			     this.nombreUsuario = nombreUsuario;
			     this.contraseña = contraseña;
			     this.rol = Rol.USER;
		}
	  
	  public static void validarNombreApellido(String nombre, String apellido) throws Exception {
			if (nombre == null || nombre.trim().isEmpty()) {
		        throw new Exception("El nombre no puede estar vacío.");
		    }
		    if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) {
		        throw new Exception("El nombre contiene caracteres inválidos.");
		    }

		    if (apellido == null || apellido.trim().isEmpty()) {
		        throw new Exception("El apellido no puede estar vacío.");
		    }
		    if (!apellido.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) {
		        throw new Exception("El apellido contiene caracteres inválidos.");
		    }
		}
		
		public static void validarNombreUsuario(String nombreUsuario) throws Exception {
		    if (nombreUsuario == null || !nombreUsuario.matches("^[a-zA-Z][a-zA-Z0-9_]{3,}$")) {
		        throw new Exception("Nombre de usuario inválido.");
		    }
		}
		
		public static void validarEmail(String email) throws Exception {
		    if (email == null || !email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
		        throw new Exception("Email inválido.");
		    }
		}
		
		public static void validarDNI(String dni) throws Exception {
		    if (dni == null || !dni.matches("\\d{7,10}")) {
		        throw new Exception("DNI inválido.");
		    }
		}

		public int getIdUsuario() {
			return idUsuario;
		}

		public void setIdUsuario(int idUsuario) {
			this.idUsuario = idUsuario;
		}

		public String getNombreUsuario() {
			return nombreUsuario;
		}

		public void setNombreUsuario(String nombreUsuario) {
			this.nombreUsuario = nombreUsuario;
		}

		public String getContraseña() {
			return contraseña;
		}

		public void setContraseña(String contraseña) {
			this.contraseña = contraseña;
		}

		public Rol getRol() {
			return rol;
		}

		public void setRol(Rol rol) {
			this.rol = rol;
		}
		
		
		
	  

}
