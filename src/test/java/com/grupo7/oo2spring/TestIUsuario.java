package com.grupo7.oo2spring;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.grupo7.oo2spring.models.Cliente;
import com.grupo7.oo2spring.models.Manager;
import com.grupo7.oo2spring.models.Usuario;
import com.grupo7.oo2spring.repositories.IUsuarioRepository;

import lombok.RequiredArgsConstructor;

@SpringBootTest
public class TestIUsuario {

	@Autowired
    private IUsuarioRepository usuarioRepository;
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Test
    void testGuardarYBuscarUsuario() {
        // Crear usuario
		try { 
			//usuarioRepository.deleteAll();
	    Usuario usuario = new Usuario("Roberto", "Jimenez", "34672169", "roberto.jimenez@example.com", "rober", "test");
	    Manager manager = new Manager(
	            "Carlos",           // nombre
	            "Gómez",            // apellido
	            "30455678",         // dni
	            "carlos@example.com", // email
	            "carlosG",          // nombreUsuario
	            "segura123"         // contraseña
	        );
        String encodedPasswordUs = passwordEncoder.encode(usuario.getContraseña());
        usuario.setContraseña(encodedPasswordUs);
        manager.setContraseña(passwordEncoder.encode(manager.getContraseña()));

     // Guardar en DB
        usuario = usuarioRepository.save(usuario);
        manager = usuarioRepository.save(manager);

        // Buscar por ID
        Optional<Usuario> encontrado = usuarioRepository.findById(usuario.getIdUsuario());
        assertThat(encontrado).isPresent();

        // Verificar datos
        assertThat(encontrado.get().getNombre()).isEqualTo("Roberto");
        assertThat(encontrado.get().getApellido()).isEqualTo("Jimenez");

        // Buscar todos los usuarios
        List<Usuario> lista = usuarioRepository.findAll();
        assertThat(lista).isNotEmpty();
        assertThat(lista).contains(usuario);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
    }
}
