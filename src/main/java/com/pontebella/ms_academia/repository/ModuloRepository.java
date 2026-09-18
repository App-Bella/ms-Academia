package com.pontebella.ms_academia.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pontebella.ms_academia.model.Modulo;

public interface ModuloRepository extends JpaRepository<Modulo, UUID> {

    List<Modulo> findByPrograma_IdOrderByOrdenAsc(UUID programaId);
}