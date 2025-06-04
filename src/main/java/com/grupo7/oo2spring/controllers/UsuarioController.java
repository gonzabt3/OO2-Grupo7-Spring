package com.grupo7.oo2spring.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.grupo7.oo2spring.models.Usuario;
import com.grupo7.oo2spring.repositories.IUsuarioRepository;
import com.grupo7.oo2spring.services.UsuarioService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final IUsuarioRepository userRepository;
    
 
    private final UsuarioService usuarioService;

    @GetMapping("/registrar")
    public String mostrarFormularioRegistro() {
    	//usuarioService.guardarUsuario("Alex","apellido", "12345678", "alex.asdasd@gmail.com", "user2000", "user200");
        return "usuario/registro_form"; // Nombre del formulario HTML
    }

    @PostMapping("/registrar")
    public String registrarUsuario(
            @RequestParam("nombre") String nombre,
            @RequestParam("apellido") String apellido,
            @RequestParam("dni") String dni,
            @RequestParam("email") String email,
            @RequestParam("nombreUsuario") String nombreUsuario,
            @RequestParam("contraseña") String contraseña,
            Model model) {
        Usuario usuarioGuardado = usuarioService.guardarUsuario(nombre, apellido, dni, email, nombreUsuario, contraseña);
        model.addAttribute("mensaje", "Usuario registrado exitosamente con ID: " + usuarioGuardado.getIdUsuario());
        return "usuario/registro_exito";
    }
    @GetMapping("/verificar/{id}")
    public String verificarUsuario(@PathVariable int id, Model model) {
        Optional<Usuario> usuarioOptional = userRepository.findById(id);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            model.addAttribute("existeUsuario", true);
            model.addAttribute("nombreUsuario", usuario.getNombre() + " " + usuario.getApellido());
            model.addAttribute("idUsuario", usuario.getIdUsuario());
        } else {
            model.addAttribute("existeUsuario", false);
            model.addAttribute("idUsuario", id);
        }
        return "usuario/verificacion"; // Nombre de la vista HTML
    }
}