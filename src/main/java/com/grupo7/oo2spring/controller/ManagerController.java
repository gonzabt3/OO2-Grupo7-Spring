package com.grupo7.oo2spring.controller;

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

import com.grupo7.oo2spring.exception.UsuarioNoEncontradoException;
import com.grupo7.oo2spring.models.Empleado;

import com.grupo7.oo2spring.models.Usuario;
import com.grupo7.oo2spring.repositories.IEmpleadoRepository;
import com.grupo7.oo2spring.repositories.IUsuarioRepository;

import com.grupo7.oo2spring.services.ManagerService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/manager")
public class ManagerController {
	
	private final IUsuarioRepository usuarioRepository;
	private final IEmpleadoRepository empleadoRepository;
   
    private final ManagerService managerService;
	
	@GetMapping("/listar")
    public String listarUsuarios(Model model) {
        List<Usuario> usuarios = usuarioRepository.findAll();
        model.addAttribute("usuarios", usuarios);
        return "manager/listar";
    }
    
	@GetMapping("/{id}/a-empleado")
	public String mostrarFormularioEmpleado(@PathVariable int id, Model model) throws UsuarioNoEncontradoException{
	        Empleado empleado = managerService.prepararEmpleadoDesdeUsuario(id);
	        model.addAttribute("empleado", empleado);
	        return "manager/formulario_empleado";
	}
    
	@PostMapping("/convertir-a-empleado")
	public String convertirAEmpleado(@ModelAttribute Empleado empleadoForm, RedirectAttributes attr) {
		try {
	        Optional<Empleado> empleadoOpt = empleadoRepository.findById(empleadoForm.getIdEmpleado());
	        if (empleadoOpt.isEmpty()) {
	            attr.addFlashAttribute("error", "Usuario no encontrado");
	            return "redirect:/manager/listar";
	        }
	
	        managerService.convertirUsuarioAEmpleado(empleadoForm.getIdEmpleado(), empleadoForm);
	
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
    	 Optional<Empleado> empleado = empleadoRepository.findById(id);
    	 
    	 if (empleado != null) {
    	        // Convertir empleado a usuario
    	        managerService.sacarPermisosEmpleado(id);
    	    }
        return "redirect:/manager/listar";
    }



}
