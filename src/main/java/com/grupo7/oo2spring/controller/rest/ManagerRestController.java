package com.grupo7.oo2spring.controller.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.grupo7.oo2spring.dto.EmpleadoDTO;
import com.grupo7.oo2spring.dto.UsuarioDTO;
import com.grupo7.oo2spring.exception.UsuarioEsEmpleadoException;
import com.grupo7.oo2spring.models.Area;
import com.grupo7.oo2spring.models.Empleado;
import com.grupo7.oo2spring.models.Rol;
import com.grupo7.oo2spring.models.Usuario;
import com.grupo7.oo2spring.models.UsuarioBase;
import com.grupo7.oo2spring.repositories.IAreaRepository;
import com.grupo7.oo2spring.repositories.IEmpleadoRepository;
import com.grupo7.oo2spring.repositories.IUsuarioRepository;
import com.grupo7.oo2spring.services.ManagerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Manager", description = "Operaciones para gestionar empleados")
@RestController
@RequestMapping("/api/manager")
@RequiredArgsConstructor
public class ManagerRestController {


	private final IEmpleadoRepository empleadoRepository;
    private final IUsuarioRepository usuarioRepository;
    private final ManagerService managerService;
    private final IAreaRepository areaRepository;
    
    @GetMapping("/listar")
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
    	List<UsuarioBase> todos = new ArrayList<>();
        todos.addAll(usuarioRepository.findAll());
        todos.addAll(empleadoRepository.findAll());

        List<UsuarioDTO> usuariosDTO = todos.stream()
            .map(UsuarioDTO::new)
            .collect(Collectors.toList());

        return ResponseEntity.ok(usuariosDTO);
    }
    

    @PreAuthorize("hasRole('MANAGER')")
    @Operation(summary = "Convierte un usuario en empleado")
    @PostMapping("/convertir/{id}")
    public ResponseEntity<String> convertirAEmpleado(@PathVariable int id, @RequestBody EmpleadoDTO dto) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Usuario no encontrado");
        }
        
        Area area = areaRepository.findById(dto.idArea())
        	    .orElseThrow(() -> new RuntimeException("Área no encontrada"));

        try {
            Empleado empleado = new Empleado(
                usuarioOpt.get().getNombre(),
                usuarioOpt.get().getApellido(),
                usuarioOpt.get().getDni(),
                usuarioOpt.get().getEmail(),
                usuarioOpt.get().getNombreUsuario(),
                usuarioOpt.get().getContraseña(),
                area,
                dto.disponibilidad()
            );
            
        

            managerService.convertirUsuarioAEmpleado(id, empleado);
            return ResponseEntity.ok("Usuario convertido en empleado exitosamente");
            
        } catch (UsuarioEsEmpleadoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error al convertir usuario");
        }
    }
    
    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("/sacar-permisos/{id}")
    public ResponseEntity<String> sacarPermisos(@PathVariable int id) {
        Optional<Empleado> empleadoOpt = empleadoRepository.findById(id);
        if (empleadoOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Empleado no encontrado");
        }

        Empleado empleado = empleadoOpt.get();
        Rol rol = empleado.getRol();
        
        System.out.println("ROL: " + rol);

        if (rol != Rol.EMPLEADO) {
            return ResponseEntity.badRequest().body("El rol no es empleado");
        }

        try {
            managerService.sacarPermisosEmpleado(id);
            return ResponseEntity.ok("Permisos sacados correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error al sacar permisos");
        }
    }


        
    }
