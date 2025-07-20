package com.grupo7.oo2spring.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class UsuarioBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	@Column(name = "username", nullable = false, unique = true)
    private String nombreUsuario;
	 @Column(name="contraseña", nullable = false)
    private String contraseña;
	 @Column(nullable = false)
    private String nombre;
	 @Column(nullable = false)
    private String apellido;
	 @Column(nullable = false)
    private String dni;
	 @Column(nullable = false, unique = true)
    protected String email;

    @ManyToOne(optional = false)
    @JoinColumn(name = "rol_id", nullable = false)
    protected Rol rol;
    
    public UsuarioBase(String nombre, String apellido, String dni, String email, String nombreUsuario, String contraseña, Rol rol) throws Exception {
		validarNombreApellido(nombre, apellido);
		validarNombreUsuario(nombreUsuario);
		validarDNI(dni);
		validarEmail(email);
		this.nombreUsuario = nombreUsuario;
		this.contraseña = contraseña;
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
		this.email = email;
    this.rol = rol;
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

      public String getApellido() {
        return this.apellido;
      }
      public void setApellido(String apellido) {
        this.apellido = apellido;
      }
    
    

	
    
    
}
