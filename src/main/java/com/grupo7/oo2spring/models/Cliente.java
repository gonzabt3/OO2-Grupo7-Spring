package com.grupo7.oo2spring.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Cliente extends Usuario {

    private String nroCliente;

    public Cliente(String nombre, String apellido, String dni, String email,
    String nombreUsuario, String contraseña, String nroCliente) throws Exception {
        super(nombre, apellido, dni, email, nombreUsuario, contraseña);
        this.nroCliente = nroCliente;
    }
}