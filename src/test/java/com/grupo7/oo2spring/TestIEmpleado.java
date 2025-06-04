package com.grupo7.oo2spring;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.grupo7.oo2spring.models.Empleado;
import com.grupo7.oo2spring.models.Area;
import com.grupo7.oo2spring.repositories.*;

@SpringBootTest
public class TestIEmpleado {
	
	@Autowired
    private IEmpleadoRepository empleadoRepository;


    @Test
    void testGuardarYBuscarEmpleado() throws Exception {
        // Crear empleado
        Empleado empleado = new Empleado("Juan", "Perez", "20.308.232", "juan.perez@example.com","juan","password", Area.DESARROLLO, true);
        // Guardar en DB
        empleado = empleadoRepository.save(empleado);

        // Buscar por ID
        Optional<Empleado> encontrado = empleadoRepository.findById(empleado.getIdUsuario());
        assertThat(encontrado).isPresent();

        // Verificar datos
        assertThat(encontrado.get().getNombre()).isEqualTo("Juan");
        assertThat(encontrado.get().getApellido()).isEqualTo("Perez");

        // Buscar todos los empleados
        List<Empleado> lista = empleadoRepository.findAll();
        assertThat(lista).isNotEmpty();
        assertThat(lista).contains(empleado);
    }

}
