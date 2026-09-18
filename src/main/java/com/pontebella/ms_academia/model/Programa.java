package com.pontebella.ms_academia.model;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "programas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Programa {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String nombre;

    @Column(length = 1000)
    private String descripcion;

    @Column(name = "duracion_horas")
    private Integer duracionHoras;

    @Column(name = "cupo_maximo", nullable = false)
    private Integer cupoMaximo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPrograma estado;

    @OneToMany(mappedBy = "programa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Modulo> modulos;

    public enum EstadoPrograma {
        ACTIVO, INACTIVO, EN_DESARROLLO
    }
}