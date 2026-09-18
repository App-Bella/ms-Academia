package com.pontebella.ms_academia.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pontebella.ms_academia.model.Matricula;

public interface MatriculaRepository extends JpaRepository<Matricula, UUID> {

    List<Matricula> findByAlumnaId(UUID alumnaId);

    List<Matricula> findByPrograma_Id(UUID programaId);

    Optional<Matricula> findByAlumnaIdAndPrograma_Id(UUID alumnaId, UUID programaId);

    List<Matricula> findByEstado(Matricula.EstadoMatricula estado);
}