package com.pontebella.ms_academia.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ModuloRequestDTO(
        @NotBlank(message = "El nombre del módulo es obligatorio")
        String nombre,

        @NotNull(message = "El orden es obligatorio")
        Integer orden,

        @NotNull(message = "Debe indicar a qué programa pertenece el módulo")
        UUID programaId
) {
}