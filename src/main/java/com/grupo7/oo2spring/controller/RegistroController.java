package com.grupo7.oo2spring.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.grupo7.oo2spring.enums.TipoRol;
import com.grupo7.oo2spring.models.EmailToken;
import com.grupo7.oo2spring.models.Rol;
import com.grupo7.oo2spring.models.Usuario;
import com.grupo7.oo2spring.repositories.IEmailTokenRepository;
import com.grupo7.oo2spring.repositories.IRolRepository;
import com.grupo7.oo2spring.repositories.IUsuarioRepository;
import com.grupo7.oo2spring.services.EmailService;

import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/usuario")
public class RegistroController {

    private final IUsuarioRepository usuarioRepository;

    private final PasswordEncoder passwordEncoder;
    
    private final IEmailTokenRepository emailTokenRepository;
    
    private final EmailService emailService;
    
    private final IRolRepository rolRepository;

    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
    	
        return "usuario/registro";
        
    }

    @Transactional
    @PostMapping("/registro")
    public String registrarUsuario(@ModelAttribute Usuario usuario, Model model, HttpServletRequest request) {
      
        
     // Validar si el nombre de usuario ya existe
        if (usuarioRepository.existsByNombreUsuario(usuario.getNombreUsuario())) {
        	
            model.addAttribute("errorUsuario", "El nombre de usuario ya está registrado.");
            model.addAttribute("usuario", usuario);
            
            return "usuario/registro"; // vuelve a mostrar el formulario con error
            
        }
        
        // Validar si el email ya existe
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
        	
            model.addAttribute("errorEmail", "El email ya está registrado.");
            model.addAttribute("usuario", usuario);  // Mantenemos el usuario con los datos ingresados
            
            return "usuario/registro"; // vuelve a mostrar el formulario con error
            
        }
        
        Rol rolUsuario = rolRepository.findByTipo(TipoRol.USER);
        
        usuario.setRol(rolUsuario);
        usuario.setContraseña(passwordEncoder.encode(usuario.getContraseña()));
        usuario.setUsuarioActivo(false);
        
        try {
        	
        usuarioRepository.save(usuario);
        
        // Generar token
        String token = UUID.randomUUID().toString();
        
        EmailToken tokenVerificacion = new EmailToken(token, usuario, LocalDateTime.now().plusDays(1));
        emailTokenRepository.save(tokenVerificacion);
        
        String asunto = "Confirmá tu cuenta";
        String confirmUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + 
                           "/usuario/confirmar?token=" + token;
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("confirmUrl", confirmUrl);
        emailService.enviarEmailConHtml(usuario.getEmail(), asunto, "email-confirmacion", variables);
        
        } catch (Exception e) {
        	
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            
            // Si algo sale mal, eliminar el usuario recién guardado
            usuarioRepository.delete(usuario);
            

            model.addAttribute("errorGeneral", "Ocurrió un error al enviar el correo de confirmación. Intentá nuevamente.");
            
            return "usuario/registro";
            
        }

        System.out.println("Usuario registrado: " + usuario.getNombreUsuario());
        
        return "redirect:/usuario/registro_exito";
        
    }
    
    @GetMapping("/registro_exito")
    public String mostrarRegistroExito() {
    	
        return "usuario/registro_exito";
        
    }
    
    @GetMapping("/token_invalido")
    public String mostrarTokenInvalido() {
    	
        return "usuario/token_invalido";
        
    }
    
    @GetMapping("/confirmar")
    public String confirmarCuenta(@RequestParam("token") String token, Model model) {

        EmailToken tokenVerificacion = emailTokenRepository.findByToken(token);

        if (tokenVerificacion == null) {
            model.addAttribute("error", "Token inválido");
            
            return "usuario/token_invalido";
            
        }

        if (tokenVerificacion.getExpiryDate().isBefore(LocalDateTime.now())) {
            model.addAttribute("error", "Token expirado");
            
            return "usuario/token_invalido";
            
        }

        Usuario usuario = tokenVerificacion.getUsuario();
        usuario.setUsuarioActivo(true);
        usuarioRepository.save(usuario);

        return "redirect:/usuario/confirmacion_exitosa";
    }
    
    @PostMapping("/reenvio_token")
    public String reenviarTokenConfirmacion(@RequestParam("email") String email, Model model, HttpServletRequest request) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        Usuario usuario = usuarioOpt.get();

        if (usuario == null) {
            model.addAttribute("mensaje", "No existe un usuario registrado con ese correo.");
            model.addAttribute("email", email);
            return "usuario/no_activo";
        }

        if (usuario.isUsuarioActivo()) {
            return "redirect:/usuario/login?error=cuenta_activa";
        }

        // Generar nuevo token
        String token = UUID.randomUUID().toString();

        EmailToken tokenVerificacion = new EmailToken(token, usuario, LocalDateTime.now().plusDays(1));
        emailTokenRepository.save(tokenVerificacion);

        // URL de confirmación
        String confirmUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                + "/usuario/confirmar?token=" + token;

        Map<String, Object> variables = new HashMap<>();
        variables.put("nombreUsuario", usuario.getNombreUsuario());
        variables.put("confirmUrl", confirmUrl);

        String asunto = "Reenvío: Confirmá tu cuenta";

        try {
            emailService.enviarEmailConHtml(usuario.getEmail(), asunto, "email_confirmacion", variables);
        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al enviar el correo de confirmación. Intentá nuevamente.");
            model.addAttribute("email", email);
            return "usuario/no_activo";
        }

        model.addAttribute("mensaje", "Se ha reenviado el correo de confirmación. Por favor revisá tu bandeja.");
        model.addAttribute("email", email);
        return "usuario/no_activo";
    }

    
    @GetMapping("/no_activo")
    public String mostrarNoActivo(Model model) {
        model.addAttribute("mensaje", "Tu cuenta aún no ha sido activada. Por favor, revisá tu correo y confirmá tu cuenta para poder iniciar sesión.");
        return "usuario/no_activo";
    }
    
    @GetMapping("/confirmacion_exitosa")
    public String mostrarConfirmacionExitosa() {
    	
        return "usuario/confirmacion_exitosa";
        
}
}