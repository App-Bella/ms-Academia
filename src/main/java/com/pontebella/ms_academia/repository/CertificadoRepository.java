package com.pontebella.ms_academia.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pontebella.ms_academia.model.Certificado;

public interface CertificadoRepository extends JpaRepository<Certificado, UUID> {

    Optional<Certificado> findByMatricula_Id(UUID matriculaId);

    Optional<Certificado> findByCodigoVerificacion(String codigoVerificacion);
}