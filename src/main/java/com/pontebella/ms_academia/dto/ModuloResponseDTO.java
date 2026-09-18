package com.pontebella.ms_academia.dto;

import java.util.UUID;

import com.pontebella.ms_academia.model.Modulo;

public record ModuloResponseDTO(
        UUID id,
        String nombre,
        Integer orden,
        UUID programaId
) {
    public static ModuloResponseDTO fromEntity(Modulo modulo) {
        return new ModuloResponseDTO(
                modulo.getId(),
                modulo.getNombre(),
                modulo.getOrden(),
                modulo.getPrograma().getId()
        );
    }
}