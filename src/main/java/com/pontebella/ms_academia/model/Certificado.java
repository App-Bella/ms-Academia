package com.pontebella.ms_academia.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "certificados")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Certificado {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matricula_id", nullable = false, unique = true)
    private Matricula matricula;

    @Column(name = "fecha_emision", nullable = false)
    private LocalDate fechaEmision;

    // Código único para validar autenticidad del certificado
    @Column(name = "codigo_verificacion", nullable = false, unique = true)
    private String codigoVerificacion;

    // Ruta o URL donde queda almacenado el PDF generado (RF-AC-03)
    @Column(name = "url_pdf")
    private String urlPdf;
}