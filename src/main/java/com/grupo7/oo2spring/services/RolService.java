package com.grupo7.oo2spring.services;

import com.grupo7.oo2spring.enums.RoleType;
import com.grupo7.oo2spring.models.Rol;
import com.grupo7.oo2spring.repositories.IRolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RolService {

    private final IRolRepository rolRepository;

    public List<Rol> listarRoles() {
        return rolRepository.findAll();
    }

    public Optional<Rol> buscarPorId(Long id) {
        return rolRepository.findById(id);
    }

    public void eliminarRol(Long id) {
        rolRepository.deleteById(id);
    }

    public Rol buscarPorTipo(RoleType roleType) {
        return rolRepository.findByType(roleType);
    }
}