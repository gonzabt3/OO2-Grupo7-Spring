package com.grupo7.oo2spring.controller.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TicketResponseDTO {
    private Integer idTicket;
    private String titulo;
    private String descripcion;
}