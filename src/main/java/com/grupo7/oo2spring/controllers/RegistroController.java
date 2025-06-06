package com.grupo7.oo2spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.grupo7.oo2spring.models.Cliente;
import com.grupo7.oo2spring.models.Rol;
import com.grupo7.oo2spring.models.Usuario;
import com.grupo7.oo2spring.repositories.IUsuarioRepository;

@Controller
public class RegistroController {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/usuario/registro")
    public String mostrarFormularioRegistro() {
        return "usuario/registro";
    }

    @PostMapping("/usuario/registro")
    public String registrarUsuario(@ModelAttribute Cliente usuario, Model model) {
      
        
     // Validar si el nombre de usuario ya existe
        if (usuarioRepository.existsByNombreUsuario(usuario.getNombreUsuario())) {
            model.addAttribute("errorUsuario", "El nombre de usuario ya está registrado.");
            model.addAttribute("usuario", usuario);
            return "/usuario/registro"; // vuelve a mostrar el formulario con error
        }
        
        // Validar si el email ya existe
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            model.addAttribute("errorEmail", "El email ya está registrado.");
            model.addAttribute("usuario", usuario);  // Mantenemos el usuario con los datos ingresados
            return "/usuario/registro"; // vuelve a mostrar el formulario con error
        }
        
        usuario.setRol(Rol.CLIENTE); // Asignar rol ROLE_CLIENTE
        usuario.setContraseña(passwordEncoder.encode(usuario.getContraseña()));

        
        usuarioRepository.save(usuario);
        System.out.println("Usuario registrado: " + usuario.getNombreUsuario());
        return "redirect:/usuario/registro_exito";
    }
    
    @GetMapping("/usuario/registro_exito")
    public String mostrarRegistroExito() {
        return "usuario/registro_exito";
    }
}

