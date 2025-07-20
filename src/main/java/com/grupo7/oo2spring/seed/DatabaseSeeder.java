package com.grupo7.oo2spring.seed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.grupo7.oo2spring.enums.AreaType;
import com.grupo7.oo2spring.enums.RoleType;
import com.grupo7.oo2spring.models.Area;
import com.grupo7.oo2spring.models.Rol;
import com.grupo7.oo2spring.repositories.IAreaRepository;
import com.grupo7.oo2spring.repositories.IRolRepository;

import java.util.Arrays;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    private IAreaRepository areaRepository;

    @Autowired
    private IRolRepository rolRepository;

    @Override
    public void run(String... args) throws Exception {
        seedAreas();
        seedRoles();
    }

    private void seedAreas() {
        if (areaRepository.count() == 0) {
            Area[] areas = new Area[]{
                buildArea(AreaType.DESARROLLO),
                buildArea(AreaType.TESTING),
                buildArea(AreaType.SOPORTE),
                buildArea(AreaType.SIN_ASIGNAR)
            };
            areaRepository.saveAll(Arrays.asList(areas));
        }
    }

    private Area buildArea(AreaType areaType) {
        return new Area(areaType);
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