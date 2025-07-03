package com.grupo7.oo2spring.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import com.grupo7.oo2spring.models.TipoArea;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "area")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Area {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(hidden = true)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private TipoArea tipo;

    public Area(TipoArea tipo) {
        this.tipo = tipo;
    }
   

}
