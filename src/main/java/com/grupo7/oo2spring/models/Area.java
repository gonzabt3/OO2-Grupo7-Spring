package com.grupo7.oo2spring.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "area")
@Data
@NoArgsConstructor
public class Area {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(hidden = true)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nombre;

    public Area(String nombre) { 
    this.nombre = nombre;
}
}