package com.grupo7.oo2spring.controller.rest;

import com.grupo7.oo2spring.dto.AreaDTO;
import com.grupo7.oo2spring.models.Area;
import com.grupo7.oo2spring.services.AreaService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/areas")
@RequiredArgsConstructor
public class AreaController {

    private final AreaService areaService;

    @GetMapping("/nombre/{nombre}")
    public AreaDTO getAreaByName(@PathVariable String nombre) {
        Area area = areaService.buscarPorNombre(nombre)
                .orElseThrow(() -> new RuntimeException("Área no encontrada: " + nombre));
        return new AreaDTO(area.getId(), area.getTipo().nombre);
    }
    

  public Area getAreaByName(@PathVariable String nombre) {
      return areaService.buscarPorNombre(nombre)
              .orElseThrow(() -> new RuntimeException("Área no encontrada: " + nombre));
  }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarArea(@PathVariable int id) {
        try {
            areaService.eliminarArea(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(404).body("No se pudo eliminar el área");
        }
    }
}