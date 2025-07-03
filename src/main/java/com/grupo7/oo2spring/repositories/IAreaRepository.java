package com.grupo7.oo2spring.repositories;

import com.grupo7.oo2spring.models.Area;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IAreaRepository extends JpaRepository<Area, Long> {
    boolean existsByNombre(String nombre);
    Optional<Area> findByNombre(String nombre);
}