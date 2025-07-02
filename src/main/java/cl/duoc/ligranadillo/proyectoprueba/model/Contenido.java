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
@Schema(description = "Entidad Contenido que representa recursos didácticos como videos o documentos")
public class Contenido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del contenido", example = "1")
    private Long id;

    @Schema(description = "Título del contenido", example = "Introducción a Java")
    private String titulo;

    @Schema(description = "Tipo de contenido", example = "Video, Documento, etc.")
    private String tipo;

    @Schema(description = "URL del contenido", example = "https://youtube.com/xyz")
    private String url;

    @Schema(description = "Descripción del contenido", example = "Video explicativo de conceptos básicos")
    private String descripcion;
}
