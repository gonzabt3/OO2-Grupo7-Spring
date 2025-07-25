package com.grupo7.oo2spring.controller.rest;

import com.grupo7.oo2spring.dto.AreaDTO;
import com.grupo7.oo2spring.models.Area;
import com.grupo7.oo2spring.services.AreaService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
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
    

    @GetMapping
    public List<Area> listarAreas() {
        return areaService.listarAreas();
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