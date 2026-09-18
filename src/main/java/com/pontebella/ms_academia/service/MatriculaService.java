package com.pontebella.ms_academia.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pontebella.ms_academia.dto.MatriculaRequestDTO;
import com.pontebella.ms_academia.dto.MatriculaResponseDTO;
import com.pontebella.ms_academia.exception.BusinessException;
import com.pontebella.ms_academia.exception.ResourceNotFoundException;
import com.pontebella.ms_academia.model.Matricula;
import com.pontebella.ms_academia.model.Programa;
import com.pontebella.ms_academia.repository.MatriculaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MatriculaService {

    private final MatriculaRepository matriculaRepository;
    private final ProgramaService programaService;
    private final CertificadoService certificadoService;

    // TODO: cuando se defina el contrato de eventos con el equipo, publicar
    // aquí "matricula.creada" por RabbitMQ (lo consume MS-Inventario para
    // descontar kits de estudio y MS-Notificaciones para el mensaje de
    // confirmación - RF-NO-01).
    public MatriculaResponseDTO matricular(MatriculaRequestDTO dto) {
        Programa programa = programaService.buscarEntidad(dto.programaId());

        if (programa.getEstado() != Programa.EstadoPrograma.ACTIVO) {
            throw new BusinessException(
                    "No se puede matricular: el programa no está activo");
        }

        long ocupados = matriculaRepository.findByPrograma_Id(programa.getId()).stream()
                .filter(m -> m.getEstado() != Matricula.EstadoMatricula.CANCELADA)
                .count();

        if (ocupados >= programa.getCupoMaximo()) {
            throw new BusinessException(
                    "No se puede matricular: el programa alcanzó su cupo máximo");
        }

        matriculaRepository.findByAlumnaIdAndPrograma_Id(dto.alumnaId(), programa.getId())
                .ifPresent(m -> {
                    throw new BusinessException(
                            "La alumna ya está matriculada en este programa");
                });

        Matricula matricula = Matricula.builder()
                .alumnaId(dto.alumnaId())
                .programa(programa)
                .fechaMatricula(LocalDate.now())
                .estado(Matricula.EstadoMatricula.ACTIVA)
                .progreso(0)
                .build();

        return MatriculaResponseDTO.fromEntity(matriculaRepository.save(matricula));
    }

    // Núcleo de RF-AC-03: al llegar a 100% se completa la matrícula
    // y se genera el certificado automáticamente.
    public MatriculaResponseDTO actualizarProgreso(UUID matriculaId, Integer nuevoProgreso) {
        if (nuevoProgreso == null || nuevoProgreso < 0 || nuevoProgreso > 100) {
            throw new BusinessException("El progreso debe estar entre 0 y 100");
        }

        Matricula matricula = buscarEntidad(matriculaId);

        if (matricula.getEstado() == Matricula.EstadoMatricula.CANCELADA) {
            throw new BusinessException("No se puede actualizar el progreso de una matrícula cancelada");
        }

        matricula.setProgreso(nuevoProgreso);

        boolean llegoAl100 = nuevoProgreso == 100;
        boolean yaEstabaCompletada = matricula.getEstado() == Matricula.EstadoMatricula.COMPLETADA;

        if (llegoAl100 && !yaEstabaCompletada) {
            matricula.setEstado(Matricula.EstadoMatricula.COMPLETADA);
            matriculaRepository.save(matricula);

            // TODO: publicar "certificado.generado" por RabbitMQ cuando se
            // defina el contrato de eventos (lo consume MS-Notificaciones).
            certificadoService.generar(matricula);
        } else {
            matriculaRepository.save(matricula);
        }

        return MatriculaResponseDTO.fromEntity(matricula);
    }

    public MatriculaResponseDTO cancelar(UUID matriculaId) {
        Matricula matricula = buscarEntidad(matriculaId);

        if (matricula.getEstado() == Matricula.EstadoMatricula.COMPLETADA) {
            throw new BusinessException("No se puede cancelar una matrícula ya completada");
        }

        matricula.setEstado(Matricula.EstadoMatricula.CANCELADA);
        return MatriculaResponseDTO.fromEntity(matriculaRepository.save(matricula));
    }

    @Transactional(readOnly = true)
    public List<MatriculaResponseDTO> listarPorAlumna(UUID alumnaId) {
        return matriculaRepository.findByAlumnaId(alumnaId).stream()
                .map(MatriculaResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<MatriculaResponseDTO> listarPorPrograma(UUID programaId) {
        return matriculaRepository.findByPrograma_Id(programaId).stream()
                .map(MatriculaResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public MatriculaResponseDTO obtenerPorId(UUID id) {
        return MatriculaResponseDTO.fromEntity(buscarEntidad(id));
    }

    private Matricula buscarEntidad(UUID id) {
        return matriculaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Matrícula no encontrada con id: " + id));
    }
}