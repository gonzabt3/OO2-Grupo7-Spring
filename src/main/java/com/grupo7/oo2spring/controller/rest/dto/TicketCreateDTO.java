package com.grupo7.oo2spring.controller.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TicketCreateDTO {
    @Schema(description = "Título del ticket", example = "No funciona la impresora", required = true)
    private String titulo;

    @Schema(description = "Descripción del problema", example = "La impresora no responde al enviar trabajos", required = true)
    private String descripcion;
}
