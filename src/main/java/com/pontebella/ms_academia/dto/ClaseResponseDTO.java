package com.pontebella.ms_academia.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.pontebella.ms_academia.model.Clase;

public record ClaseResponseDTO(
        UUID id,
        String nombre,
        LocalDateTime fecha,
        UUID docenteId,
        UUID moduloId
) {
    public static ClaseResponseDTO fromEntity(Clase clase) {
        return new ClaseResponseDTO(
                clase.getId(),
                clase.getNombre(),
                clase.getFecha(),
                clase.getDocenteId(),
                clase.getModulo().getId()
        );
    }
}