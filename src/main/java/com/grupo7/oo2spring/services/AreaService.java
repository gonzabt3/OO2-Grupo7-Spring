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

    public Optional<Area> buscarPorId(int id) {
        return areaRepository.findById(id);
    }
    
    public void eliminarArea(int id) {
        areaRepository.deleteById(id);
    }

}