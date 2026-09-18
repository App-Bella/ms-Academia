package com.pontebella.ms_academia.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "matriculas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Matricula {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // Referencia externa: la alumna vive en MS-Usuarios (RGA-01)
    @Column(name = "alumna_id", nullable = false)
    private UUID alumnaId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "programa_id", nullable = false)
    private Programa programa;

    @Column(name = "fecha_matricula", nullable = false)
    private LocalDate fechaMatricula;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoMatricula estado;

    // Porcentaje de avance 0-100. Al llegar a 100 se dispara RF-AC-03.
    @Column(nullable = false)
    @Builder.Default
    private Integer progreso = 0;

    @OneToOne(mappedBy = "matricula", cascade = CascadeType.ALL, orphanRemoval = true)
    private Certificado certificado;

    public enum EstadoMatricula {
        ACTIVA, COMPLETADA, CANCELADA
    }
}