package com.grupo7.oo2spring.dto;


import com.grupo7.oo2spring.models.Area;
import com.grupo7.oo2spring.models.Rol;

import lombok.NoArgsConstructor;

import com.grupo7.oo2spring.models.Area;
import com.grupo7.oo2spring.models.Rol;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public record EmpleadoDTO(
    @Schema(description = "ID del usuario", example = "4")
    int idUsuario,

    @Schema(description = "Área del nuevo empleado")
    Area area,

    @Schema(description = "Disponibilidad del empleado", example = "true")
    boolean disponibilidad
) {}
	
	
	
	
