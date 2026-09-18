package com.pontebella.ms_academia.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pontebella.ms_academia.model.Programa;

public interface ProgramaRepository extends JpaRepository<Programa, UUID> {

    List<Programa> findByEstado(Programa.EstadoPrograma estado);

    List<Programa> findByNombreContainingIgnoreCase(String nombre);
}