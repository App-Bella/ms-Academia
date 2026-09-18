package com.pontebella.ms_academia.dto;

import java.util.UUID;

import com.pontebella.ms_academia.model.Programa;

public record ProgramaResponseDTO(
        UUID id,
        String nombre,
        String descripcion,
        Integer duracionHoras,
        Integer cupoMaximo,
        Programa.EstadoPrograma estado
) {
    public static ProgramaResponseDTO fromEntity(Programa programa) {
        return new ProgramaResponseDTO(
                programa.getId(),
                programa.getNombre(),
                programa.getDescripcion(),
                programa.getDuracionHoras(),
                programa.getCupoMaximo(),
                programa.getEstado()
        );
    }
}