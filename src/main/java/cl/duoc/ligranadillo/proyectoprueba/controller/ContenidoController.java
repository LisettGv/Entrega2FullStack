package cl.duoc.ligranadillo.proyectoprueba.controller;

import cl.duoc.ligranadillo.proyectoprueba.model.Contenido;
import cl.duoc.ligranadillo.proyectoprueba.service.ContenidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v2/contenidos")
@Tag(name = "Contenidos", description = "Microservicio para gestión de contenidos")
public class ContenidoController {

    @Autowired
    private ContenidoService contenidoService;

    @PostMapping("/crear")
    @Operation(summary = "Crear contenido", description = "Registra un nuevo contenido")
    public ResponseEntity<Map<String, Object>> crearContenido(@RequestBody Contenido contenido) {
        Contenido creado = contenidoService.guardarContenido(contenido);
        return ResponseEntity.status(201).body(Map.of(
                "message", "Contenido creado exitosamente",
                "contenido", creado
        ));
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar contenidos", description = "Obtiene todos los contenidos")
    public ResponseEntity<Map<String, Object>> listarContenidos() {
        List<Contenido> lista = contenidoService.obtenerContenidos();
        return ResponseEntity.ok(Map.of(
                "message", "Contenidos obtenidos correctamente",
                "total", lista.size(),
                "data", lista
        ));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener contenido por ID", description = "Obtiene un contenido por su ID")
    public ResponseEntity<Map<String, Object>> obtenerContenido(@PathVariable Long id) {
        return contenidoService.obtenerContenidoPorId(id)
                .map(c -> ResponseEntity.ok(Map.of("message", "Contenido encontrado", "contenido", c)))
                .orElse(ResponseEntity.status(404).body(Map.of("message", "Contenido con ID " + id + " no encontrado")));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar contenido", description = "Actualiza un contenido existente")
    public ResponseEntity<Map<String, Object>> actualizarContenido(@PathVariable Long id, @RequestBody Contenido contenido) {
        return contenidoService.actualizarContenido(id, contenido)
                .map(c -> ResponseEntity.ok(Map.of("message", "Contenido actualizado correctamente", "contenido", c)))
                .orElse(ResponseEntity.status(404).body(Map.of("message", "No se pudo actualizar: contenido con ID " + id + " no encontrado")));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar contenido", description = "Elimina un contenido existente")
    public ResponseEntity<Map<String, Object>> eliminarContenido(@PathVariable Long id) {
        boolean eliminado = contenidoService.eliminarContenido(id);
        return eliminado
                ? ResponseEntity.ok(Map.of("message", "Contenido eliminado correctamente"))
                : ResponseEntity.status(404).body(Map.of("message", "Contenido con ID " + id + " no encontrado"));
    }
}
