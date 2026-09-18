package com.pontebella.ms_academia.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pontebella.ms_academia.dto.ModuloRequestDTO;
import com.pontebella.ms_academia.dto.ModuloResponseDTO;
import com.pontebella.ms_academia.exception.ResourceNotFoundException;
import com.pontebella.ms_academia.model.Modulo;
import com.pontebella.ms_academia.model.Programa;
import com.pontebella.ms_academia.repository.ModuloRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ModuloService {

    private final ModuloRepository moduloRepository;
    private final ProgramaService programaService;

    public ModuloResponseDTO crear(ModuloRequestDTO dto) {
        Programa programa = programaService.buscarEntidad(dto.programaId());

        Modulo modulo = Modulo.builder()
                .nombre(dto.nombre())
                .orden(dto.orden())
                .programa(programa)
                .build();

        return ModuloResponseDTO.fromEntity(moduloRepository.save(modulo));
    }

    @Transactional(readOnly = true)
    public List<ModuloResponseDTO> listarPorPrograma(UUID programaId) {
        return moduloRepository.findByPrograma_IdOrderByOrdenAsc(programaId).stream()
                .map(ModuloResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public ModuloResponseDTO obtenerPorId(UUID id) {
        return ModuloResponseDTO.fromEntity(buscarEntidad(id));
    }

    public ModuloResponseDTO actualizar(UUID id, ModuloRequestDTO dto) {
        Modulo modulo = buscarEntidad(id);
        modulo.setNombre(dto.nombre());
        modulo.setOrden(dto.orden());

        if (!modulo.getPrograma().getId().equals(dto.programaId())) {
            modulo.setPrograma(programaService.buscarEntidad(dto.programaId()));
        }

        return ModuloResponseDTO.fromEntity(moduloRepository.save(modulo));
    }

    public void eliminar(UUID id) {
        moduloRepository.delete(buscarEntidad(id));
    }

    protected Modulo buscarEntidad(UUID id) {
        return moduloRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Módulo no encontrado con id: " + id));
    }
}