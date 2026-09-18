package com.pontebella.ms_academia.dto;

import java.time.LocalDate;
import java.util.UUID;

import com.pontebella.ms_academia.model.Certificado;

// Sin RequestDTO: el certificado siempre se genera automáticamente
// por el sistema (RF-AC-03), nunca se crea manualmente vía API.
public record CertificadoResponseDTO(
        UUID id,
        UUID matriculaId,
        LocalDate fechaEmision,
        String codigoVerificacion,
        String urlPdf
) {
    public static CertificadoResponseDTO fromEntity(Certificado certificado) {
        return new CertificadoResponseDTO(
                certificado.getId(),
                certificado.getMatricula().getId(),
                certificado.getFechaEmision(),
                certificado.getCodigoVerificacion(),
                certificado.getUrlPdf()
        );
    }
}