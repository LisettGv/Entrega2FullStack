package cl.duoc.ligranadillo.proyectoprueba.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Schema(description = "Entidad que representa un curso impartido por la plataforma")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del curso", example = "1")
    private Long id;

    @Schema(description = "Nombre del curso", example = "Java Básico")
    private String nombre;

    @Schema(description = "Descripción del curso", example = "Introducción a Java")
    private String descripcion;

    @Schema(description = "Categoría del curso", example = "Programación")
    private String categoria;

    @Schema(description = "Fecha de inicio del curso", example = "2025-07-01")
    private String fechaInicio;

    @Schema(description = "Fecha de término del curso", example = "2025-08-01")
    private String fechaFin;

    @Schema(description = "Nombre del instructor del curso", example = "Juan Pérez")
    private String instructor;
}
