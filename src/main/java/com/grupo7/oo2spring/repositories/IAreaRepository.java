package com.grupo7.oo2spring.repositories;

import com.grupo7.oo2spring.models.Area;
<<<<<<< HEAD
import com.grupo7.oo2spring.models.TipoArea;
=======
>>>>>>> 78ddca7 (remplazo area enum por entidad en la db)

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

<<<<<<< HEAD
public interface IAreaRepository extends JpaRepository<Area, Integer> {
    boolean existsByTipo(TipoArea tipo);
    Optional<Area> findByTipo(TipoArea tipo);
=======
public interface IAreaRepository extends JpaRepository<Area, Long> {
    boolean existsByNombre(String nombre);
    Optional<Area> findByNombre(String nombre);
>>>>>>> 78ddca7 (remplazo area enum por entidad en la db)
}