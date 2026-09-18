package com.pontebella.ms_academia.service;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pontebella.ms_academia.dto.CertificadoResponseDTO;
import com.pontebella.ms_academia.exception.ResourceNotFoundException;
import com.pontebella.ms_academia.model.Certificado;
import com.pontebella.ms_academia.model.Matricula;
import com.pontebella.ms_academia.repository.CertificadoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CertificadoService {

    private final CertificadoRepository certificadoRepository;

    // Se llama internamente desde MatriculaService cuando progreso llega a 100
    // (RF-AC-03). No se expone como endpoint de creación manual.
    public Certificado generar(Matricula matricula) {
        Certificado certificado = Certificado.builder()
                .matricula(matricula)
                .fechaEmision(LocalDate.now())
                .codigoVerificacion(generarCodigoVerificacion())
                // TODO: integrar generación real de PDF (ej. con OpenPDF/iText)
                // y subirlo a almacenamiento; por ahora queda pendiente.
                .urlPdf(null)
                .build();

        return certificadoRepository.save(certificado);
    }

    @Transactional(readOnly = true)
    public CertificadoResponseDTO obtenerPorMatricula(UUID matriculaId) {
        Certificado certificado = certificadoRepository.findByMatricula_Id(matriculaId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Esta matrícula aún no tiene certificado generado"));

        return CertificadoResponseDTO.fromEntity(certificado);
    }

    // Endpoint público de verificación de autenticidad del certificado.
    @Transactional(readOnly = true)
    public CertificadoResponseDTO verificar(String codigoVerificacion) {
        Certificado certificado = certificadoRepository.findByCodigoVerificacion(codigoVerificacion)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Código de verificación inválido"));

        return CertificadoResponseDTO.fromEntity(certificado);
    }

    private String generarCodigoVerificacion() {
        return "PB-" + UUID.randomUUID().toString()
                .replace("-", "")
                .substring(0, 12)
                .toUpperCase();
    }
}