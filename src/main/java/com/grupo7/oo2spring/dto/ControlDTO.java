package com.grupo7.oo2spring.dto;

import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "DTO para la transferencia de datos de un Control/Intervención")

public record ControlDTO (
    @Schema(description = "ID único del control", example = "101", accessMode = Schema.AccessMode.READ_ONLY)
    int idControl,

    @Schema(description = "ID del ticket asociado", example = "5", accessMode = Schema.AccessMode.READ_ONLY)
    int idTicket,

    @Schema(description = "Acción o descripción de la intervención realizada", example = "Se analizó el log de errores y se escaló el caso.", required = true)
    @NotBlank(message = "La acción no puede estar vacía")
    String accion,

    @Schema(description = "Tipo de función o rol de la intervención (ej. ESCALAMIENTO, SEGUIMIENTO)", example = "SEGUIMIENTO", required = true)
    @NotNull(message = "La función es obligatoria")
    String funcion, 

    @Schema(description = "¿La intervención ha finalizado?", example = "false")
    boolean finalizado,

    @Schema(description = "Fecha y hora de inicio de la intervención", example = "2024-07-25T10:00:00", accessMode = Schema.AccessMode.READ_ONLY)
    LocalDateTime fechaEntrada,

    @Schema(description = "Fecha y hora de finalización de la intervención", example = "2024-07-25T11:30:00", nullable = true, accessMode = Schema.AccessMode.READ_ONLY)
    LocalDateTime fechaSalida,

    @Schema(description = "Nombre completo del empleado que realizó la intervención", example = "Juan Pérez", accessMode = Schema.AccessMode.READ_ONLY)
    String empleado, 

    @Schema(description = "Título del ticket al que pertenece el control", example = "Problema con el servicio de correo", accessMode = Schema.AccessMode.READ_ONLY)
    String tituloTicket 
	) {}
