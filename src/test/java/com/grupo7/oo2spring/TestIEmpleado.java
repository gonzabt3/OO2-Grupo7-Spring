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
import com.grupo7.oo2spring.models.Area;
import com.grupo7.oo2spring.repositories.*;

@SpringBootTest
public class TestIEmpleado {
	
	@Autowired
    private IUsuarioRepository empleadoRepository;
	@Autowired
    private PasswordEncoder passwordEncoder;


    @Test
    void testGuardarYBuscarEmpleado() throws Exception {
        // Crear empleado
    	//empleadoRepository.deleteById(4);
        Empleado empleado = new Empleado("Juan", "Perez", "20308232", "juan.perez@example.com","juan", passwordEncoder.encode("password"), Area.DESARROLLO, true);
        empleado = empleadoRepository.save(empleado);

        // Buscar por ID
        Optional<Empleado> encontrado = empleadoRepository.findEmpleadoById(empleado.getIdUsuario());
        assertThat(encontrado).isPresent();
        
        System.out.println("Rol del empleado creado: " + empleado.getRol());

        // Verificar datos
        assertThat(encontrado.get().getNombre()).isEqualTo("Juan");
        assertThat(encontrado.get().getApellido()).isEqualTo("Perez");

        // Buscar todos los empleados
        List<Empleado> lista = empleadoRepository.findAllEmpleados();
        assertThat(lista).isNotEmpty();
        assertThat(lista).contains(empleado);
    }

}
