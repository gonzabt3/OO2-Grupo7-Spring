package com.grupo7.oo2spring.models;

import com.grupo7.oo2spring.enums.RoleType;
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
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "nombre", nullable = false, unique = true, length = 80)
    private RoleType type;

    public Rol(@NotNull RoleType type) {
        this.type = type;
    }

    public String getNombre() {
        return type.toString();
    }
}