package com.grupo7.oo2spring.models;

import com.grupo7.oo2spring.enums.AreaType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "nombre", nullable = false, unique = true, length = 80)
    private AreaType type;

    public Area(@NotNull AreaType type) {
        this.type = type;
    }

    public String getNombre() {
        return type.toString();
    }
}