package com.grupo7.oo2spring.seed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.grupo7.oo2spring.models.Area;
import com.grupo7.oo2spring.models.TipoArea;
import com.grupo7.oo2spring.repositories.IAreaRepository;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {

    private final IAreaRepository areaRepository;


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
            Rol[] roles = new Rol[]{
                new Rol(RoleType.USER),
                new Rol(RoleType.MANAGER),
                new Rol(RoleType.EMPLEADO)
            };
            rolRepository.saveAll(Arrays.asList(roles));
        }
    }
    
    private void seedRoles() {
        if (rolRepository.count() == 0) {
            Rol[] roles = new Rol[]{
                new Rol(RoleType.USER),
                new Rol(RoleType.MANAGER),
                new Rol(RoleType.EMPLEADO)
            };
            rolRepository.saveAll(Arrays.asList(roles));
        }
    }
}
}