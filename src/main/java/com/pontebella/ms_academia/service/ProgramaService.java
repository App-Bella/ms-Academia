package com.pontebella.ms_academia.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pontebella.ms_academia.dto.ProgramaRequestDTO;
import com.pontebella.ms_academia.dto.ProgramaResponseDTO;
import com.pontebella.ms_academia.exception.ResourceNotFoundException;
import com.pontebella.ms_academia.model.Programa;
import com.pontebella.ms_academia.repository.ProgramaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProgramaService {

    private final ProgramaRepository programaRepository;

    public ProgramaResponseDTO crear(ProgramaRequestDTO dto) {
        Programa programa = Programa.builder()
                .nombre(dto.nombre())
                .descripcion(dto.descripcion())
                .duracionHoras(dto.duracionHoras())
                .cupoMaximo(dto.cupoMaximo())
                .estado(dto.estado())
                .build();

        return ProgramaResponseDTO.fromEntity(programaRepository.save(programa));
    }

    @Transactional(readOnly = true)
    public List<ProgramaResponseDTO> listar() {
        return programaRepository.findAll().stream()
                .map(ProgramaResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProgramaResponseDTO obtenerPorId(UUID id) {
        return ProgramaResponseDTO.fromEntity(buscarEntidad(id));
    }

    public ProgramaResponseDTO actualizar(UUID id, ProgramaRequestDTO dto) {
        Programa programa = buscarEntidad(id);
        programa.setNombre(dto.nombre());
        programa.setDescripcion(dto.descripcion());
        programa.setDuracionHoras(dto.duracionHoras());
        programa.setCupoMaximo(dto.cupoMaximo());
        programa.setEstado(dto.estado());

        return ProgramaResponseDTO.fromEntity(programaRepository.save(programa));
    }

    // RF-AC-01: "desactivación" del programa, no borrado físico.
    public void desactivar(UUID id) {
        Programa programa = buscarEntidad(id);
        programa.setEstado(Programa.EstadoPrograma.INACTIVO);
        programaRepository.save(programa);
    }

    protected Programa buscarEntidad(UUID id) {
        return programaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Programa no encontrado con id: " + id));
    }
}