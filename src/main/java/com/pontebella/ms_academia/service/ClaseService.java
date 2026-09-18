package com.pontebella.ms_academia.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pontebella.ms_academia.dto.ClaseRequestDTO;
import com.pontebella.ms_academia.dto.ClaseResponseDTO;
import com.pontebella.ms_academia.exception.ResourceNotFoundException;
import com.pontebella.ms_academia.model.Clase;
import com.pontebella.ms_academia.model.Modulo;
import com.pontebella.ms_academia.repository.ClaseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ClaseService {

    private final ClaseRepository claseRepository;
    private final ModuloService moduloService;

    public ClaseResponseDTO crear(ClaseRequestDTO dto) {
        Modulo modulo = moduloService.buscarEntidad(dto.moduloId());

        Clase clase = Clase.builder()
                .nombre(dto.nombre())
                .fecha(dto.fecha())
                .docenteId(dto.docenteId())
                .modulo(modulo)
                .build();

        return ClaseResponseDTO.fromEntity(claseRepository.save(clase));
    }

    @Transactional(readOnly = true)
    public List<ClaseResponseDTO> listarPorModulo(UUID moduloId) {
        return claseRepository.findByModulo_Id(moduloId).stream()
                .map(ClaseResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ClaseResponseDTO> listarPorDocente(UUID docenteId) {
        return claseRepository.findByDocenteId(docenteId).stream()
                .map(ClaseResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public ClaseResponseDTO obtenerPorId(UUID id) {
        return ClaseResponseDTO.fromEntity(buscarEntidad(id));
    }

    public ClaseResponseDTO actualizar(UUID id, ClaseRequestDTO dto) {
        Clase clase = buscarEntidad(id);
        clase.setNombre(dto.nombre());
        clase.setFecha(dto.fecha());
        clase.setDocenteId(dto.docenteId());

        if (!clase.getModulo().getId().equals(dto.moduloId())) {
            clase.setModulo(moduloService.buscarEntidad(dto.moduloId()));
        }

        return ClaseResponseDTO.fromEntity(claseRepository.save(clase));
    }

    public void eliminar(UUID id) {
        claseRepository.delete(buscarEntidad(id));
    }

    private Clase buscarEntidad(UUID id) {
        return claseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Clase no encontrada con id: " + id));
    }
}