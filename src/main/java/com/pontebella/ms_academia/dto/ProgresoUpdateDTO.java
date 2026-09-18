package com.pontebella.ms_academia.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ProgresoUpdateDTO(
        @NotNull(message = "El progreso es obligatorio")
        @Min(value = 0, message = "El progreso no puede ser menor a 0")
        @Max(value = 100, message = "El progreso no puede ser mayor a 100")
        Integer progreso
) {
}