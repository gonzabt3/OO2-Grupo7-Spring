package com.grupo7.oo2spring.controller;

import com.grupo7.oo2spring.models.Area;
import com.grupo7.oo2spring.services.AreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/areas")
@RequiredArgsConstructor
public class AreaWebController {

    private final AreaService areaService;

    @GetMapping
    public String listarAreas(Model model) {
        model.addAttribute("areas", areaService.listarAreas());
        return "areas/lista"; // nombre del template para listar
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevaArea(Model model) {
        model.addAttribute("area", new Area());
        return "areas/form"; // nombre del template para crear/editar
    }


    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarArea(@PathVariable Long id, Model model) {
        Area area = areaService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Área no encontrada: " + id));
        model.addAttribute("area", area);
        return "areas/form";
    }


    @GetMapping("/eliminar/{id}")
    public String eliminarArea(@PathVariable Long id) {
        areaService.eliminarArea(id);
        return "redirect:/areas";
    }
}