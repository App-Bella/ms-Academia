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

import com.pontebella.ms_academia.dto.ModuloRequestDTO;
import com.pontebella.ms_academia.dto.ModuloResponseDTO;
import com.pontebella.ms_academia.service.ModuloService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/modulos")
@RequiredArgsConstructor
public class ModuloController {

    private final ModuloService moduloService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ModuloResponseDTO crear(@Valid @RequestBody ModuloRequestDTO dto) {
        return moduloService.crear(dto);
    }

    @GetMapping("/programa/{programaId}")
    public List<ModuloResponseDTO> listarPorPrograma(@PathVariable UUID programaId) {
        return moduloService.listarPorPrograma(programaId);
    }

    @GetMapping("/{id}")
    public ModuloResponseDTO obtenerPorId(@PathVariable UUID id) {
        return moduloService.obtenerPorId(id);
    }

    @PutMapping("/{id}")
    public ModuloResponseDTO actualizar(@PathVariable UUID id,
                                         @Valid @RequestBody ModuloRequestDTO dto) {
        return moduloService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        moduloService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}