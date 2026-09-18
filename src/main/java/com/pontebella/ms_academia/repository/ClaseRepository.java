package com.pontebella.ms_academia.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pontebella.ms_academia.model.Clase;

public interface ClaseRepository extends JpaRepository<Clase, UUID> {

    List<Clase> findByModulo_Id(UUID moduloId);

    List<Clase> findByDocenteId(UUID docenteId);
}