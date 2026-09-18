package com.pontebella.ms_academia.dto;

import java.time.LocalDate;
import java.util.UUID;

import com.pontebella.ms_academia.model.Matricula;

public record MatriculaResponseDTO(
        UUID id,
        UUID alumnaId,
        UUID programaId,
        LocalDate fechaMatricula,
        Matricula.EstadoMatricula estado,
        Integer progreso
) {
    public static MatriculaResponseDTO fromEntity(Matricula matricula) {
        return new MatriculaResponseDTO(
                matricula.getId(),
                matricula.getAlumnaId(),
                matricula.getPrograma().getId(),
                matricula.getFechaMatricula(),
                matricula.getEstado(),
                matricula.getProgreso()
        );
    }
}