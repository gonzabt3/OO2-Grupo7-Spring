package com.grupo7.oo2spring;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.grupo7.oo2spring.models.Area;
import com.grupo7.oo2spring.models.Estado;
import com.grupo7.oo2spring.models.Prioridad;
import com.grupo7.oo2spring.models.Ticket;
import com.grupo7.oo2spring.enums.TipoArea;
import com.grupo7.oo2spring.models.Usuario;
import com.grupo7.oo2spring.models.UsuarioBase;
import com.grupo7.oo2spring.repositories.IAreaRepository;
import com.grupo7.oo2spring.repositories.ITicketRepository;
import com.grupo7.oo2spring.repositories.IUsuarioBaseRepository;
import com.grupo7.oo2spring.repositories.IUsuarioRepository;

@SpringBootTest
public class TestITicket {
	
	 @Autowired
	    private ITicketRepository ticketRepository;

	    @Autowired
	    private IUsuarioRepository usuarioRepository;
	    
	    @Autowired
	    private IAreaRepository areaRepository;
	    
	    @Autowired
	    private IUsuarioBaseRepository usuarioBaseRepository;
	    

	    
	    @Test
	    public void testCrearTicketsParaUsuarioExistente() {
	    	ticketRepository.deleteAll();
		    Optional<UsuarioBase> usuarioOpt = usuarioBaseRepository.findByNombreUsuario("rober");
		    UsuarioBase roberto = usuarioOpt.get();

	    for (TipoArea area : TipoArea.values()) {
            if (area == TipoArea.SIN_ASIGNAR) continue;

            for (Prioridad prioridad : Prioridad.values()) {
                if (prioridad == Prioridad.SIN_ASIGNAR) continue;

    		    Optional<Area> areaOpt = areaRepository.findByTipo(area);
    		    Area areaExistente = areaOpt.get();
                for (Estado estado : Estado.values()) {
                    Ticket ticket = new Ticket(
                            "Título para " + areaExistente.getTipo(),
                            "Descripción con prioridad " + prioridad + " y estado " + estado,
                            roberto
                    );
                    ticket.setArea(areaExistente);
                    ticket.setPrioridad(prioridad);
                    ticket.setEstado(estado);

                    ticketRepository.save(ticket);
                }
            }
	    }
	    }

}