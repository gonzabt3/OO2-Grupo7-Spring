package com.grupo7.oo2spring.models;

import com.grupo7.oo2spring.enums.TipoRol;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "rol")
@Data
@NoArgsConstructor
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(hidden = true)
    private int id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "nombre", nullable = false, unique = true, length = 80)
    private TipoRol tipo;

    public Rol(@NotNull TipoRol tipo) {
        this.tipo = tipo;
    }
    
    

}