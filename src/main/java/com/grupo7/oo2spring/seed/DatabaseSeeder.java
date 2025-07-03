package com.grupo7.oo2spring.seed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.grupo7.oo2spring.models.Area;
import com.grupo7.oo2spring.repositories.IAreaRepository;

import java.util.Arrays;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    private IAreaRepository areaRepository;

    @Override
    public void run(String... args) throws Exception {
        seedAreas();
    }

    private void seedAreas() {
        if (areaRepository.count() == 0) {
            Area[] areas = {
                new Area("Desarrollo"),
                new Area("Testing"),
                new Area("Soporte"),
                new Area("SIN_ASIGNAR")
            };
            areaRepository.saveAll(Arrays.asList(areas));
        }
    }
}