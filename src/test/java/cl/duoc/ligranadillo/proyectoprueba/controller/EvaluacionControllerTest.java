package cl.duoc.ligranadillo.proyectoprueba.controller;

import cl.duoc.ligranadillo.proyectoprueba.model.Evaluacion;
import cl.duoc.ligranadillo.proyectoprueba.service.EvaluacionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EvaluacionControllerTest {

    private EvaluacionService evaluacionService;
    private EvaluacionController evaluacionController;

    @BeforeEach
    void setUp() {
        evaluacionService = mock(EvaluacionService.class);
        evaluacionController = new EvaluacionController();
        // "Hack" para setear el campo privado sin setter
        var field = EvaluacionController.class.getDeclaredFields()[0];
        field.setAccessible(true);
        try {
            field.set(evaluacionController, evaluacionService);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testCrearEvaluacion() {
        Evaluacion evaluacion = new Evaluacion(null, "Parcial Spring", "Cuestionario", 100, "5");
        Evaluacion guardada = new Evaluacion(1L, "Parcial Spring", "Cuestionario", 100, "5");

        when(evaluacionService.guardarEvaluacion(evaluacion)).thenReturn(guardada);

        ResponseEntity<Map<String, Object>> response = evaluacionController.crearEvaluacion(evaluacion);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Evaluación creada exitosamente", response.getBody().get("message"));
        assertEquals(guardada, response.getBody().get("evaluacion"));
    }

    @Test
    void testListarEvaluaciones() {
        List<Evaluacion> lista = List.of(
                new Evaluacion(1L, "Prueba Java", "Cuestionario", 100, "2"),
                new Evaluacion(2L, "Proyecto Final", "Tarea", 200, "3")
        );

        when(evaluacionService.obtenerEvaluaciones()).thenReturn(lista);

        ResponseEntity<Map<String, Object>> response = evaluacionController.listarEvaluaciones();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().get("total"));
        assertEquals(lista, response.getBody().get("data"));
    }

    @Test
    void testObtenerEvaluacionExistente() {
        Evaluacion e = new Evaluacion(1L, "Examen", "Oral", 50, "1");

        when(evaluacionService.obtenerEvaluacionPorId(1L)).thenReturn(Optional.of(e));

        ResponseEntity<Map<String, Object>> response = evaluacionController.obtenerEvaluacion(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Evaluación encontrada", response.getBody().get("message"));
        assertEquals(e, response.getBody().get("evaluacion"));
    }

    @Test
    void testObtenerEvaluacionNoExistente() {
        when(evaluacionService.obtenerEvaluacionPorId(99L)).thenReturn(Optional.empty());

        ResponseEntity<Map<String, Object>> response = evaluacionController.obtenerEvaluacion(99L);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Evaluación con ID 99 no encontrada", response.getBody().get("message"));
    }

    @Test
    void testActualizarEvaluacionExistente() {
        Evaluacion actualizada = new Evaluacion(1L, "Parcial Java Modificado", "Cuestionario", 110, "2");

        when(evaluacionService.actualizarEvaluacion(eq(1L), any(Evaluacion.class)))
                .thenReturn(Optional.of(actualizada));

        ResponseEntity<Map<String, Object>> response = evaluacionController.actualizarEvaluacion(1L, actualizada);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Evaluación actualizada correctamente", response.getBody().get("message"));
        assertEquals(actualizada, response.getBody().get("evaluacion"));
    }

    @Test
    void testActualizarEvaluacionNoExistente() {
        when(evaluacionService.actualizarEvaluacion(eq(42L), any(Evaluacion.class)))
                .thenReturn(Optional.empty());

        ResponseEntity<Map<String, Object>> response = evaluacionController.actualizarEvaluacion(42L, new Evaluacion());

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("No se pudo actualizar: evaluación con ID 42 no encontrada", response.getBody().get("message"));
    }

    @Test
    void testEliminarEvaluacionExistente() {
        when(evaluacionService.eliminarEvaluacion(1L)).thenReturn(true);

        ResponseEntity<Map<String, Object>> response = evaluacionController.eliminarEvaluacion(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Evaluación eliminada correctamente", response.getBody().get("message"));
    }

    @Test
    void testEliminarEvaluacionNoExistente() {
        when(evaluacionService.eliminarEvaluacion(100L)).thenReturn(false);

        ResponseEntity<Map<String, Object>> response = evaluacionController.eliminarEvaluacion(100L);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Evaluación con ID 100 no encontrada", response.getBody().get("message"));
    }
}
