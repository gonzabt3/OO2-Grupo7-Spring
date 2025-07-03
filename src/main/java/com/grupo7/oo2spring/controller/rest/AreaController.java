package com.grupo7.oo2spring.controller.rest;

import com.grupo7.oo2spring.controller.rest.dto.AreaDTO;
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
    public List<AreaDTO> listarAreas() {
        return areaService.listarAreas()
                .stream()
                .map(area -> new AreaDTO(area.getId(), area.getNombre()))
                .toList();
    }

    @GetMapping("/nombre/{nombre}")
    public AreaDTO getAreaByName(@PathVariable String nombre) {
        Area area = areaService.buscarPorNombre(nombre)
                .orElseThrow(() -> new RuntimeException("Área no encontrada: " + nombre));
        return new AreaDTO(area.getId(), area.getNombre());
    }

    @PostMapping
    public AreaDTO crearAreaSiNoExiste(@RequestBody AreaDTO areaDTO) {
        Area area = areaService.crearAreaSiNoExiste(areaDTO.nombre());
        return new AreaDTO(area.getId(), area.getNombre());
    }

    @PutMapping("/{id}")
    public AreaDTO actualizarArea(@PathVariable Long id, @RequestBody AreaDTO areaDTO) {
        Area areaObj = new Area();
        areaObj.setId(areaDTO.id());
        areaObj.setNombre(areaDTO.nombre());
        Area area = areaService.actualizarArea(id, areaObj);
        return new AreaDTO(area.getId(), area.getNombre());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarArea(@PathVariable Long id) {
        try {
            areaService.eliminarArea(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(404).body("No se pudo eliminar el área");
        }
    }
}