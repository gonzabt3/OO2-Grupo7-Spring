package com.grupo7.oo2spring.controller.rest;

import com.grupo7.oo2spring.models.Area;
import com.grupo7.oo2spring.services.AreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/areas")
@RequiredArgsConstructor
public class AreaController {

    private final AreaService areaService;

    @GetMapping
    public List<Area> listarAreas() {
        return areaService.listarAreas();
    }

    @GetMapping("/nombre/{nombre}")
  public Area getAreaByName(@PathVariable String nombre) {
      return areaService.buscarPorNombre(nombre)
              .orElseThrow(() -> new RuntimeException("Área no encontrada: " + nombre));
  }


    @DeleteMapping("/{id}")
    public void eliminarArea(@PathVariable int id) {
        areaService.eliminarArea(id);
    }
}