package com.grupo7.oo2spring.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.grupo7.oo2spring.models.Cliente;
import com.grupo7.oo2spring.models.Empleado;
import com.grupo7.oo2spring.models.Rol;
import com.grupo7.oo2spring.models.Ticket;
import com.grupo7.oo2spring.models.Usuario;
import com.grupo7.oo2spring.repositories.IEmpleadoRepository;
import com.grupo7.oo2spring.repositories.ITicketRepository;
import com.grupo7.oo2spring.repositories.IUsuarioRepository;
import com.grupo7.oo2spring.services.EmpleadoService;
import com.grupo7.oo2spring.services.ManagerService;
import com.grupo7.oo2spring.services.TicketService;
import com.grupo7.oo2spring.services.UsuarioService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/manager")
public class ManagerController {
	
	private final IUsuarioRepository usuarioRepository;
    private final EmpleadoService empleadoService;
    private final UsuarioService usuarioService;
    private final ITicketRepository ticketRepository;
    private final IEmpleadoRepository empleadoRepository;
    private final ManagerService managerService;
	
	@GetMapping("/listar")
    public String listarUsuarios(Model model) {
        List<Usuario> usuarios = usuarioRepository.findAll();
        model.addAttribute("usuarios", usuarios);
        return "manager/listar";
    }
    
	@GetMapping("/{id}/a-empleado")
	public String mostrarFormularioEmpleado(@PathVariable int id, Model model) {
		 try {
		        Empleado empleado = managerService.prepararEmpleadoDesdeUsuario(id);
		        model.addAttribute("empleado", empleado);
		        return "manager/formulario_empleado";
		    } catch (Exception e) {
		        e.printStackTrace();
		        return "redirect:/manager/listar";
		    }
	}
    
	@PostMapping("/convertir-a-empleado")
	public String convertirAEmpleado(@ModelAttribute Empleado empleadoForm, RedirectAttributes attr) {
		try {
	        Optional<Usuario> usuarioOpt = usuarioRepository.findById(empleadoForm.getIdUsuario());
	        if (usuarioOpt.isEmpty()) {
	            attr.addFlashAttribute("error", "Usuario no encontrado");
	            return "redirect:/manager/listar";
	        }

	        managerService.convertirUsuarioAEmpleado(empleadoForm.getIdUsuario(), empleadoForm);

	        attr.addFlashAttribute("success", "Usuario convertido en empleado exitosamente");
	        return "redirect:/manager/listar";

	    } catch (Exception e) {
	        e.printStackTrace();
	        attr.addFlashAttribute("error", "Error al convertir usuario en empleado");
	        return "redirect:/manager/listar";
	    }
	}
    
    @PostMapping("/{id}/sacar-permisos")
    public String sacarPermisos(@PathVariable int id) throws Exception {
        // lógica para sacar permisos
    	 Optional<Empleado> empleado = usuarioRepository.findEmpleadoById(id);
    	 
    	 if (empleado != null) {
    	        // Convertir empleado a usuario
    	        managerService.sacarPermisosEmpleado(id);
    	    }
        return "redirect:/manager/listar";
    }



}
