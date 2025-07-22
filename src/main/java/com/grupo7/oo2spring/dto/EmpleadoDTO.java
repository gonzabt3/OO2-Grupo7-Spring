package com.grupo7.oo2spring.dto;

import com.grupo7.oo2spring.models.Area;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para convertir un usuario en empleado")
public record EmpleadoDTO(

    @Schema(description = "Id del Empleado", example = "5")
    int idEmpleado,

    @Schema(description = "Área asignada al empleado", example = "SOPORTE")
    int areaId,

    @Schema(description = "Disponibilidad actual del empleado", example = "true")
    boolean disponibilidad

) {}
