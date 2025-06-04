package com.grupo7.oo2spring;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testCrearTicket() throws Exception {
        mockMvc.perform(post("/tickets/crear")
                .param("titulo", "Error en login")
                .param("descripcion", "No puedo ingresar con mi usuario")
                .param("usuarioCreador.idUsuario", "1")
                .param("usuarioCreador.nombre", "Roberto")
                .param("usuarioCreador.email", "roberto.jimenez@example.com"))
                .andExpect(status().isOk());
    }
}

