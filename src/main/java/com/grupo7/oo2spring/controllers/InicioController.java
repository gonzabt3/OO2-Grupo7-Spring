package com.grupo7.oo2spring.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class InicioController {

    @GetMapping({"/", "/inicio"})
    public String raiz(Model model) {
    	  return "index"; 
    }
    

    
}