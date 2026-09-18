package com.pontebella.ms_academia.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

// Al matricularse no se envía progreso ni estado: el servicio los
// inicializa (progreso = 0, estado = ACTIVA) y fechaMatricula = hoy.
public record MatriculaRequestDTO(
        @NotNull(message = "Debe indicar la alumna a matricular")
        UUID alumnaId,

        @NotNull(message = "Debe indicar el programa")
        UUID programaId
) {
}