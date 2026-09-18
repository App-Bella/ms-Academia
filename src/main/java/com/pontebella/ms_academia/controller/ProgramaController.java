package com.pontebella.ms_academia.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pontebella.ms_academia.dto.ProgramaRequestDTO;
import com.pontebella.ms_academia.dto.ProgramaResponseDTO;
import com.pontebella.ms_academia.service.ProgramaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/programas")
@RequiredArgsConstructor
public class ProgramaController {

    private final ProgramaService programaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProgramaResponseDTO crear(@Valid @RequestBody ProgramaRequestDTO dto) {
        return programaService.crear(dto);
    }

    @GetMapping
    public List<ProgramaResponseDTO> listar() {
        return programaService.listar();
    }

    @GetMapping("/{id}")
    public ProgramaResponseDTO obtenerPorId(@PathVariable UUID id) {
        return programaService.obtenerPorId(id);
    }

    @PutMapping("/{id}")
    public ProgramaResponseDTO actualizar(@PathVariable UUID id,
                                           @Valid @RequestBody ProgramaRequestDTO dto) {
        return programaService.actualizar(id, dto);
    }

    // RF-AC-01: desactivación (no borrado físico)
    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Void> desactivar(@PathVariable UUID id) {
        programaService.desactivar(id);
        return ResponseEntity.noContent().build();
    }
}