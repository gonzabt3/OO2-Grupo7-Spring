package com.grupo7.oo2spring.repositories;

import java.util.Optional;

import com.grupo7.oo2spring.enums.AreaType;
import com.grupo7.oo2spring.enums.RoleType;
import com.grupo7.oo2spring.models.Area;
import com.grupo7.oo2spring.models.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  IRolRepository extends JpaRepository<Rol, Integer> {

    // Métodos personalizados si es necesario


    boolean existsByType(RoleType type);
    Rol findByType(RoleType type);

  
}