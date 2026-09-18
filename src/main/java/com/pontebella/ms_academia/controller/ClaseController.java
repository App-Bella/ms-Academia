package com.pontebella.ms_academia.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pontebella.ms_academia.dto.ClaseRequestDTO;
import com.pontebella.ms_academia.dto.ClaseResponseDTO;
import com.pontebella.ms_academia.service.ClaseService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/clases")
@RequiredArgsConstructor
public class ClaseController {

    private final ClaseService claseService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClaseResponseDTO crear(@Valid @RequestBody ClaseRequestDTO dto) {
        return claseService.crear(dto);
    }

    @GetMapping("/modulo/{moduloId}")
    public List<ClaseResponseDTO> listarPorModulo(@PathVariable UUID moduloId) {
        return claseService.listarPorModulo(moduloId);
    }

    @GetMapping("/docente/{docenteId}")
    public List<ClaseResponseDTO> listarPorDocente(@PathVariable UUID docenteId) {
        return claseService.listarPorDocente(docenteId);
    }

    @GetMapping("/{id}")
    public ClaseResponseDTO obtenerPorId(@PathVariable UUID id) {
        return claseService.obtenerPorId(id);
    }

    @PutMapping("/{id}")
    public ClaseResponseDTO actualizar(@PathVariable UUID id,
                                        @Valid @RequestBody ClaseRequestDTO dto) {
        return claseService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        claseService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}