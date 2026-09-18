package com.pontebella.ms_academia.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ClaseRequestDTO(
        @NotBlank(message = "El nombre de la clase es obligatorio")
        String nombre,

        @NotNull(message = "La fecha es obligatoria")
        LocalDateTime fecha,

        @NotNull(message = "Debe indicar el docente asignado")
        UUID docenteId,

        @NotNull(message = "Debe indicar a qué módulo pertenece la clase")
        UUID moduloId
) {
}