package com.grupo7.oo2spring.controller.rest;

import com.grupo7.oo2spring.controller.rest.dto.TicketCreateDTO;
import com.grupo7.oo2spring.controller.rest.dto.TicketResponseDTO;
import com.grupo7.oo2spring.dto.TicketDTO;
import com.grupo7.oo2spring.exception.TicketNoEncontradoException;
import com.grupo7.oo2spring.models.Ticket;
import com.grupo7.oo2spring.models.Usuario;
import com.grupo7.oo2spring.models.UsuarioBase;
import com.grupo7.oo2spring.security.UsuarioDetails;
import com.grupo7.oo2spring.services.TicketService;
import com.grupo7.oo2spring.services.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
@Tag(name = "Tickets", description = "Operaciones CRUD para tickets")
public class TicketRestController {

    private final TicketService ticketService;

    private final UsuarioService usuarioService;

    @Operation(summary = "Obtener todos los tickets del usuario logueado")
    @GetMapping
    public ResponseEntity<List<TicketResponseDTO>> getAll(@AuthenticationPrincipal UsuarioDetails usuario) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UsuarioDetails usuarioDetails = (UsuarioDetails) auth.getPrincipal();
        String nombreDelUsuarioEnSesion = usuarioDetails.getUsername();

        Usuario usuarioCreador = usuarioService.getUsuarioByNombreUsuario(nombreDelUsuarioEnSesion);
        if (usuarioCreador == null) {
            return ResponseEntity.notFound().build();
        }
        List<Ticket> tickets = ticketService.findByUsuario(usuarioCreador);
        List<TicketResponseDTO> response = tickets.stream()
                .map(t -> new TicketResponseDTO(t.getIdTicket(), t.getTitulo(), t.getDescripcion()))
                .toList();
        return ResponseEntity.ok(response);
    }


@Operation(summary = "Crear un nuevo ticket")
@PostMapping
public ResponseEntity<TicketResponseDTO> create(@RequestBody TicketCreateDTO ticketDTO, @AuthenticationPrincipal UsuarioDetails usuario) {
    TicketDTO ticket = new TicketDTO();
    ticket.setTitulo(ticketDTO.getTitulo());
    ticket.setDescripcion(ticketDTO.getDescripcion());
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    UsuarioDetails usuarioDetails = (UsuarioDetails) auth.getPrincipal();
    String nombreDelUsuarioEnSesion = usuarioDetails.getUsername();

    Usuario usuarioCreador = usuarioService.getUsuarioByNombreUsuario(nombreDelUsuarioEnSesion);
    if (usuarioCreador == null) {
        return ResponseEntity.notFound().build();
    }
    Ticket creado = ticketService.crearTicket(ticket, usuarioCreador);

    TicketResponseDTO response = new TicketResponseDTO(
        creado.getIdTicket(),
        creado.getTitulo(),
        creado.getDescripcion()
    );
    return ResponseEntity.ok(response);
}

    @Operation(summary = "Actualizar un ticket existente (solo si es autor)")
    @PutMapping("/{id}")
    public ResponseEntity<TicketResponseDTO> update(@PathVariable int id, @RequestBody TicketCreateDTO ticketDTO, @AuthenticationPrincipal UsuarioDetails usuario) throws TicketNoEncontradoException {
        Ticket existente = ticketService.buscarTicketPorId(id);
        if (existente == null || existente.getUsuarioCreador().getId() != usuario.getUsuario().getId()) {
            return ResponseEntity.status(403).build();
        }
        existente.setTitulo(ticketDTO.getTitulo());
        existente.setDescripcion(ticketDTO.getDescripcion());
        Ticket actualizado = ticketService.update(id, existente);
        TicketResponseDTO response = new TicketResponseDTO(
            actualizado.getIdTicket(),
            actualizado.getTitulo(),
            actualizado.getDescripcion()
        );
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Eliminar un ticket (solo si es autor)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id, @AuthenticationPrincipal UsuarioDetails usuario) throws TicketNoEncontradoException {
        Ticket existente = ticketService.buscarTicketPorId(id);
        if (existente == null || existente.getUsuarioCreador().getId() != usuario.getUsuario().getId()) {
            return ResponseEntity.status(403).build();
        }
        ticketService.delete(id);
        return ResponseEntity.noContent().build();
    }
}