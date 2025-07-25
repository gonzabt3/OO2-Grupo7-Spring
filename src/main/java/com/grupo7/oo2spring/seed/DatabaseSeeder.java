package com.grupo7.oo2spring.seed;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.grupo7.oo2spring.enums.TipoArea;
import com.grupo7.oo2spring.enums.TipoRol;
import com.grupo7.oo2spring.models.Area;
import com.grupo7.oo2spring.models.Rol;
import com.grupo7.oo2spring.repositories.IAreaRepository;
import com.grupo7.oo2spring.repositories.IRolRepository;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {

    private final IAreaRepository areaRepository;
    private final IRolRepository rolRepository;


    @Override
    public void run(String... args) throws Exception {
        seedAreas();
        seedRoles();
    }

    private void seedAreas() {
        if (areaRepository.count() == 0) {
            for (TipoArea tipo : TipoArea.values()) {
                areaRepository.save(new Area(tipo));
            }
        }
    }    
    private void seedRoles() {
        if (rolRepository.count() == 0) {
        	for (TipoRol tipo: TipoRol.values()) {
        		rolRepository.save(new Rol(tipo));
        	}
        }
    }

}