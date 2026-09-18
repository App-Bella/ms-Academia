package com.pontebella.ms_academia.controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pontebella.ms_academia.dto.CertificadoResponseDTO;
import com.pontebella.ms_academia.service.CertificadoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/certificados")
@RequiredArgsConstructor
public class CertificadoController {

    private final CertificadoService certificadoService;

    @GetMapping("/matricula/{matriculaId}")
    public CertificadoResponseDTO obtenerPorMatricula(@PathVariable UUID matriculaId) {
        return certificadoService.obtenerPorMatricula(matriculaId);
    }

    // Endpoint público para validar la autenticidad de un certificado
    @GetMapping("/verificar/{codigo}")
    public CertificadoResponseDTO verificar(@PathVariable String codigo) {
        return certificadoService.verificar(codigo);
    }
}