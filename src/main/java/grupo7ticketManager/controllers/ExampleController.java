package grupo7ticketManager.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/examples")
public class ExampleController {

    @GetMapping
    public String getExamples() {
        return "Lista de ejemplos";
    }

    @PostMapping
    public String createExample() {
        return "Ejemplo creado";
    }
}