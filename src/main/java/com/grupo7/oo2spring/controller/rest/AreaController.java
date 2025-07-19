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
                .map(area -> new AreaDTO(area.getId(), area.getType().toString()))
                .toList();
    }

}