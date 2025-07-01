package com.grupo7.oo2spring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FaviconController {

	@RequestMapping("favicon.ico")
	public ResponseEntity<Void> favicon() {
		
	    return ResponseEntity.noContent().build();  // Respuesta 204 sin contenido
	}
}
