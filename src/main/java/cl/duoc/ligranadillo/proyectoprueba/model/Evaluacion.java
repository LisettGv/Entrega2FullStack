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
@Schema(description = "Entidad que representa una evaluación en el sistema")
public class Evaluacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único de la evaluación", example = "1")
    private Long id;

    @Schema(description = "Título de la evaluación", example = "Prueba Parcial de Java")
    private String titulo;

    @Schema(description = "Tipo de evaluación (por ejemplo: cuestionario, tarea)", example = "Cuestionario")
    private String tipo;

    @Schema(description = "Puntaje máximo alcanzable en la evaluación", example = "100")
    private int puntajeMaximo;

    @Schema(description = "Identificador del curso asociado", example = "3")
    private String cursoId;
}
