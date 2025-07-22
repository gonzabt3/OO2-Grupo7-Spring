package com.grupo7.oo2spring.controller.rest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupo7.oo2spring.dto.AreaDTO;
import com.grupo7.oo2spring.models.TipoArea;
import com.grupo7.oo2spring.repositories.IAreaRepository;
import com.grupo7.oo2spring.services.UsuarioService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/area")
public class AreaRestController {
	
	private final IAreaRepository areaRepository;

	@GetMapping("/listar")
	public List<AreaDTO> listarAreas() {
	    return areaRepository.findAll().stream()
	        .map(area -> new AreaDTO(area.getId(), area.getTipo().name()))
	        .toList();
	}
}