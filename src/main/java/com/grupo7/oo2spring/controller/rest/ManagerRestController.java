
package com.grupo7.oo2spring.controller.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupo7.oo2spring.models.Empleado;
import com.grupo7.oo2spring.models.Usuario;
import com.grupo7.oo2spring.repositories.IUsuarioRepository;
import com.grupo7.oo2spring.services.ManagerService;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/managers")
public class ManagerRestController {
	private final IUsuarioRepository usuarioRepository;
    private final ManagerService managerService;
    
    @GetMapping("/listar") // Mapea GET a /api/usuarios
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll(); // Misma lógica
        return ResponseEntity.ok(usuarios); // ¡Devuelve la lista como JSON con 200 OK!
    }
    
    @GetMapping("/{id}/datos-conversion-empleado") // Un nombre de ruta más claro para REST
    public ResponseEntity<Empleado> getDatosParaConversionAEmpleado(@PathVariable int id) {
        try {
            // Aquí la lógica es la misma: prepara el objeto Empleado con datos del Usuario
            Empleado empleado = managerService.prepararEmpleadoDesdeUsuario(id);
            return ResponseEntity.ok(empleado); // Devuelve el objeto Empleado como JSON
        } catch (Exception e) {
            // Manejo de errores REST:
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 si usuario no encontrado
        }
    }
    
    @PostMapping("/convertir") // Ruta para el endpoint REST
    public ResponseEntity<?> convertirUsuarioAEmpleadoRest(@RequestBody Empleado empleadoData) { // Recibe JSON
        try {
            // La lógica de validación y llamada al servicio es la misma
            Empleado empleadoConvertido = managerService.convertirUsuarioAEmpleado(empleadoData.getIdEmpleado(), empleadoData);

            // Respuesta REST: 200 OK, 201 Created si es nueva conversión, o 4xx si hay errores
            return ResponseEntity.ok(empleadoConvertido); // Devuelve el Empleado convertido como JSON
        } catch (IllegalArgumentException e) { // Ejemplo de manejo de errores más específico
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\": \"Error interno al convertir: " + e.getMessage() + "\"}");
        }
    }
    
    @PostMapping("/{id}/sacar-permisos") // Ruta para el endpoint REST
    public ResponseEntity<?> sacarPermisosRest(@PathVariable int id) {
    	Optional<Empleado> empleado = usuarioRepository.findEmpleadoById(id);
        try {
            if (empleado !=null ) {
            	managerService.sacarPermisosEmpleado(id);
                return ResponseEntity.ok().body("{\"message\": \"Permisos de empleado revocados exitosamente\"}"); // 200 OK
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"message\": \"Empleado no encontrado para sacar permisos\"}"); // 404 si no existe
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\": \"Error al sacar permisos: " + e.getMessage() + "\"}");
        }
    }

}
