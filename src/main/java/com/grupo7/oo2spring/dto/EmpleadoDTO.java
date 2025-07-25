package com.grupo7.oo2spring.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para convertir un usuario en empleado")
public record EmpleadoDTO(

    @Schema(description = "Id del Empleado", example = "5")
    int idEmpleado,

    @Schema(description = "Id de Área asignada al empleado", example = "SOPORTE")
    int idArea,

    @Schema(description = "Disponibilidad actual del empleado", example = "true")
    boolean disponibilidad

) {}
