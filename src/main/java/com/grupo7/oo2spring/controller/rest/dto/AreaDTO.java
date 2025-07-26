package com.grupo7.oo2spring.controller.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record AreaDTO(@Schema(hidden = true) Long id,     
String nombre) {}