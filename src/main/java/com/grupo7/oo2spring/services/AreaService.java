package com.grupo7.oo2spring.services;

import com.grupo7.oo2spring.models.Area;
import com.grupo7.oo2spring.repositories.IAreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AreaService {

    private final IAreaRepository areaRepository;

    public List<Area> listarAreas() {
        return areaRepository.findAll();
    }

    public Optional<Area> buscarPorId(Long id) {
        return areaRepository.findById(id);
    }

    public Area crearAreaSiNoExiste(String nombre) {
        return areaRepository.findByNombre(nombre)
            .orElseGet(() -> areaRepository.save(new Area(nombre)));
    }

    public Area actualizarArea(Long id, Area area) {
        Area existente = areaRepository.findById(id).orElseThrow();
        existente.setNombre(area.getNombre());
        return areaRepository.save(existente);
    }

    public void eliminarArea(Long id) {
        areaRepository.deleteById(id);
    }

    public Optional<Area> buscarPorNombre(String nombre) {
      return areaRepository.findByNombre(nombre);
    }
}