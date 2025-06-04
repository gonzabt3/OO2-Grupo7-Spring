package com.grupo7.oo2spring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo7.oo2spring.models.Ticket;
import com.grupo7.oo2spring.models.Usuario;
import com.grupo7.oo2spring.repositories.ITicketRepository;

@Service
public class TicketService {

    @Autowired
    private ITicketRepository ticketRepository;
    
    public List<Ticket> getTicketsByUsuario(Usuario usuario) {
        return ticketRepository.findByUsuarioCreadorIdUsuario(usuario.getIdUsuario());
    }
  
}
