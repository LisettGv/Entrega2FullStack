package cl.duoc.ligranadillo.proyectoprueba.controller;

import cl.duoc.ligranadillo.proyectoprueba.model.Curso;
import cl.duoc.ligranadillo.proyectoprueba.service.CursoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v2/cursos")
@Tag(name = "Cursos", description = "Microservicio para gestión de cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @PostMapping("/crear")
    @Operation(summary = "Crear curso", description = "Registra un nuevo curso con sus atributos")
    public ResponseEntity<Map<String, Object>> crearCurso(@RequestBody Curso curso) {
        Curso creado = cursoService.guardarCurso(curso);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "message", "Curso creado exitosamente",
                "curso", creado
        ));
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar cursos", description = "Obtiene todos los cursos registrados")
    public ResponseEntity<Map<String, Object>> listarCursos() {
        List<Curso> cursos = cursoService.obtenerCursos();
        return ResponseEntity.ok(Map.of(
                "message", "Cursos obtenidos correctamente",
                "total", cursos.size(),
                "data", cursos
        ));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener curso por ID", description = "Retorna un curso específico según su ID")
    public ResponseEntity<Map<String, Object>> obtenerCurso(@PathVariable Long id) {
        return cursoService.obtenerCursoPorId(id)
                .map(curso -> ResponseEntity.ok(Map.of(
                        "message", "Curso encontrado",
                        "curso", curso
                )))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "Curso con ID " + id + " no encontrado")));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar curso", description = "Actualiza un curso existente según su ID")
    public ResponseEntity<Map<String, Object>> actualizarCurso(@PathVariable Long id, @RequestBody Curso curso) {
        return cursoService.actualizarCurso(id, curso)
                .map(actualizado -> ResponseEntity.ok(Map.of(
                        "message", "Curso actualizado correctamente",
                        "curso", actualizado
                )))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "No se pudo actualizar: curso con ID " + id + " no encontrado")));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar curso", description = "Elimina un curso existente por su ID")
    public ResponseEntity<Map<String, Object>> eliminarCurso(@PathVariable Long id) {
        boolean eliminado = cursoService.eliminarCurso(id);
        if (eliminado) {
            return ResponseEntity.ok(Map.of("message", "Curso eliminado correctamente"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Curso con ID " + id + " no encontrado"));
        }
    }
}
