package com.pontebella.ms_academia.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pontebella.ms_academia.dto.MatriculaRequestDTO;
import com.pontebella.ms_academia.dto.MatriculaResponseDTO;
import com.pontebella.ms_academia.dto.ProgresoUpdateDTO;
import com.pontebella.ms_academia.service.MatriculaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/matriculas")
@RequiredArgsConstructor
public class MatriculaController {

    private final MatriculaService matriculaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MatriculaResponseDTO matricular(@Valid @RequestBody MatriculaRequestDTO dto) {
        return matriculaService.matricular(dto);
    }

    // RF-AC-03: al llegar a 100 se completa la matrícula y se genera el certificado.
    @PatchMapping("/{id}/progreso")
    public MatriculaResponseDTO actualizarProgreso(@PathVariable UUID id,
                                                     @Valid @RequestBody ProgresoUpdateDTO dto) {
        return matriculaService.actualizarProgreso(id, dto.progreso());
    }

    @PatchMapping("/{id}/cancelar")
    public MatriculaResponseDTO cancelar(@PathVariable UUID id) {
        return matriculaService.cancelar(id);
    }

    @GetMapping("/alumna/{alumnaId}")
    public List<MatriculaResponseDTO> listarPorAlumna(@PathVariable UUID alumnaId) {
        return matriculaService.listarPorAlumna(alumnaId);
    }

    @GetMapping("/programa/{programaId}")
    public List<MatriculaResponseDTO> listarPorPrograma(@PathVariable UUID programaId) {
        return matriculaService.listarPorPrograma(programaId);
    }

    @GetMapping("/{id}")
    public MatriculaResponseDTO obtenerPorId(@PathVariable UUID id) {
        return matriculaService.obtenerPorId(id);
    }
}