package com.grupo7.oo2spring;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.grupo7.oo2spring.models.Empleado;
import com.grupo7.oo2spring.models.Rol;
import com.grupo7.oo2spring.models.Area;
import com.grupo7.oo2spring.repositories.*;
import com.grupo7.oo2spring.services.AreaService;

@SpringBootTest
public class TestIEmpleado {
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	@Autowired
	private IEmpleadoRepository empleadoRepository;
    @Autowired
    private AreaService areaService;


    @Test
    void testGuardarYBuscarEmpleado() throws Exception {
        // Crear empleado
        empleadoRepository.deleteAll(); // Limpia la tabla antes de crear empleados
        Area area = areaService.crearAreaSiNoExiste("Soporte");
        
        final Empleado empleado = empleadoRepository.save(
            new Empleado("Juan", "Perez", "20308232", "juan.perez@example.com","juan", passwordEncoder.encode("password"), area, true)
        );
        
	    Empleado manager = new Empleado(
	            "Carlos",           // nombre
	            "Gómez",            // apellido
	            "30455678",         // dni
	            "carlos@example.com", // email
	            "carlosG",          // nombreUsuario
	            "segura123",         // contraseña
	            area, 
	            true
	        );
	    
        manager.setContraseña(passwordEncoder.encode(manager.getContraseña()));
        manager.setRol(Rol.MANAGER);
	    manager = empleadoRepository.save(manager);

        // Buscar por ID
        Optional<Empleado> encontrado = empleadoRepository.findById(empleado.getIdEmpleado());
        assertThat(encontrado).isPresent();
        
        System.out.println("Rol del empleado creado: " + empleado.getRol());

        // Verificar datos
        assertThat(encontrado.get().getNombre()).isEqualTo("Juan");
        assertThat(encontrado.get().getApellido()).isEqualTo("Perez");

        // Buscar todos los empleados
        List<Empleado> lista = empleadoRepository.findAll();
        assertThat(lista).isNotEmpty();
        assertThat(lista.stream().anyMatch(e -> e.getIdEmpleado() == empleado.getIdEmpleado())).isTrue();
    }

}
