package com.grupo7.oo2spring.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.grupo7.oo2spring.models.Empleado;
import com.grupo7.oo2spring.models.Usuario;
import com.grupo7.oo2spring.security.UsuarioDetails;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @GetMapping("/misDatos")
    public String verificarUsuario(Model model, @AuthenticationPrincipal UserDetails usuariolog) {
    	UsuarioDetails usuarioDetail = (UsuarioDetails)usuariolog;
    	Object entidadLogeada = usuarioDetail.getUsuario();
    	if (entidadLogeada instanceof Empleado) {
            Empleado empleadoLogeado = (Empleado) entidadLogeada;
            model.addAttribute("usuariologueado", empleadoLogeado); // Objeto común para propiedades compartidas
            model.addAttribute("tipoEntidad", "empleado"); // Indicador de tipo
            model.addAttribute("empleadoData", empleadoLogeado); // Datos específicos de Empleado

        } else if (entidadLogeada instanceof Usuario) {
            Usuario usuarioLogeado = (Usuario) entidadLogeada;
            model.addAttribute("usuariologueado", usuarioLogeado); // Objeto común para propiedades compartidas
            model.addAttribute("tipoEntidad", "usuario"); // Indicador de tipo
            model.addAttribute("usuarioData", usuarioLogeado); // Datos específicos de Usuario
        }
        return "usuario/datos_usuario";
    }
}
