package com.grupo7.oo2spring.controllers;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.grupo7.oo2spring.models.EmailToken;
import com.grupo7.oo2spring.models.Rol;
import com.grupo7.oo2spring.models.Usuario;
import com.grupo7.oo2spring.repositories.IEmailTokenRepository;
import com.grupo7.oo2spring.repositories.IUsuarioRepository;
import com.grupo7.oo2spring.services.EmailService;
import com.grupo7.oo2spring.services.UsuarioService;
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
        
        usuario.setRol(Rol.USER);
        usuario.setContraseña(passwordEncoder.encode(usuario.getContraseña()));
        usuario.setUsuarioActivo(true);
        
        try {
        usuarioRepository.save(usuario);
        
        // Generar token
        String token = UUID.randomUUID().toString();
        
        EmailToken tokenVerificacion = new EmailToken(token, usuario, LocalDateTime.now().plusDays(1));
        emailTokenRepository.save(tokenVerificacion);
        
        String asunto = "Confirmá tu cuenta";
        String confirmUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + 
                           "/usuario/confirmar?token=" + token;
        System.out.println("Guardo el token");
        String cuerpo = "<html><body>"
                + "<h2>¡Gracias por registrarte!</h2>"
                + "<p>Para confirmar tu cuenta, hacé clic en el siguiente botón:</p>"
                + "<p><a href='" + confirmUrl + "' "
                + "style='display:inline-block;padding:10px 20px;background-color:#4CAF50;color:white;"
                + "text-decoration:none;border-radius:5px;'>Confirmar cuenta</a></p>"
                + "<p>Si no te registraste en nuestro sitio, podés ignorar este mensaje.</p>"
                + "</body></html>";
        emailService.enviarEmail(usuario.getEmail(), asunto, cuerpo);
        } catch (Exception e) {
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
    
    @GetMapping("/confirmacion_exitosa")
    public String mostrarConfirmacionExitosa() {
        return "usuario/confirmacion_exitosa";
}
}