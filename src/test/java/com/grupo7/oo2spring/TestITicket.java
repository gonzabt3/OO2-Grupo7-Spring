package com.grupo7.oo2spring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.grupo7.oo2spring.models.Area;
import com.grupo7.oo2spring.models.Estado;
import com.grupo7.oo2spring.models.Prioridad;
import com.grupo7.oo2spring.models.Ticket;
import com.grupo7.oo2spring.models.TipoArea;
import com.grupo7.oo2spring.models.Usuario;
import com.grupo7.oo2spring.repositories.ITicketRepository;
import com.grupo7.oo2spring.repositories.IUsuarioRepository;

@SpringBootTest
public class TestITicket {
	
	 @Autowired
	    private ITicketRepository ticketRepository;

	    @Autowired
	    private IUsuarioRepository usuarioRepository;
	    

	 /*   
	    @Test
	    public void testCrearTicketsParaUsuarioExistente() {
		    Usuario roberto = usuarioRepository.findByNombreUsuario("rober");
	    for (TipoArea area : TipoArea.values()) {
            if (area == TipoArea.SIN_ASIGNAR) continue;

            for (Prioridad prioridad : Prioridad.values()) {
                if (prioridad == Prioridad.SIN_ASIGNAR) continue;

                for (Estado estado : Estado.values()) {
                    Ticket ticket = new Ticket(
                            "Título para " + area,
                            "Descripción con prioridad " + prioridad + " y estado " + estado,
                            roberto
                    );
                    ticket.setArea(new Area(area));
                    ticket.setPrioridad(prioridad);
                    ticket.setEstado(estado);

                    ticketRepository.save(ticket);
                }
            }
	    }
	    }
 */
}
