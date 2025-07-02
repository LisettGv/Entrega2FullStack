package cl.duoc.ligranadillo.proyectoprueba.controller;

import cl.duoc.ligranadillo.proyectoprueba.model.Evaluacion;
import cl.duoc.ligranadillo.proyectoprueba.service.EvaluacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v2/evaluaciones")
@Tag(name = "Evaluaciones", description = "Microservicio para gestión de evaluaciones")
public class EvaluacionController {

    @Autowired
    private EvaluacionService evaluacionService;

    @PostMapping("/crear")
    @Operation(summary = "Crear evaluación", description = "Registra una nueva evaluación")
    public ResponseEntity<Map<String, Object>> crearEvaluacion(@RequestBody Evaluacion evaluacion) {
        Evaluacion creada = evaluacionService.guardarEvaluacion(evaluacion);
        return ResponseEntity.status(201).body(Map.of(
                "message", "Evaluación creada exitosamente",
                "evaluacion", creada
        ));
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar evaluaciones", description = "Obtiene todas las evaluaciones")
    public ResponseEntity<Map<String, Object>> listarEvaluaciones() {
        List<Evaluacion> lista = evaluacionService.obtenerEvaluaciones();
        return ResponseEntity.ok(Map.of(
                "message", "Evaluaciones obtenidas correctamente",
                "total", lista.size(),
                "data", lista
        ));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener evaluación por ID", description = "Obtiene una evaluación por su ID")
    public ResponseEntity<Map<String, Object>> obtenerEvaluacion(@PathVariable Long id) {
        return evaluacionService.obtenerEvaluacionPorId(id)
                .map(e -> ResponseEntity.ok(Map.of("message", "Evaluación encontrada", "evaluacion", e)))
                .orElse(ResponseEntity.status(404).body(Map.of("message", "Evaluación con ID " + id + " no encontrada")));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar evaluación", description = "Actualiza una evaluación existente")
    public ResponseEntity<Map<String, Object>> actualizarEvaluacion(@PathVariable Long id, @RequestBody Evaluacion evaluacion) {
        return evaluacionService.actualizarEvaluacion(id, evaluacion)
                .map(e -> ResponseEntity.ok(Map.of("message", "Evaluación actualizada correctamente", "evaluacion", e)))
                .orElse(ResponseEntity.status(404).body(Map.of("message", "No se pudo actualizar: evaluación con ID " + id + " no encontrada")));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar evaluación", description = "Elimina una evaluación existente")
    public ResponseEntity<Map<String, Object>> eliminarEvaluacion(@PathVariable Long id) {
        boolean eliminado = evaluacionService.eliminarEvaluacion(id);
        return eliminado
                ? ResponseEntity.ok(Map.of("message", "Evaluación eliminada correctamente"))
                : ResponseEntity.status(404).body(Map.of("message", "Evaluación con ID " + id + " no encontrada"));
    }
}
