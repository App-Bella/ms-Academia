package com.pontebella.ms_academia.dto;

import com.pontebella.ms_academia.model.Programa;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProgramaRequestDTO(
        @NotBlank(message = "El nombre del programa es obligatorio")
        String nombre,

        String descripcion,

        @Positive(message = "La duración debe ser un número positivo de horas")
        Integer duracionHoras,

        @NotNull(message = "El cupo máximo es obligatorio")
        @Positive(message = "El cupo máximo debe ser mayor a 0")
        Integer cupoMaximo,

        @NotNull(message = "El estado es obligatorio")
        Programa.EstadoPrograma estado
) {
}