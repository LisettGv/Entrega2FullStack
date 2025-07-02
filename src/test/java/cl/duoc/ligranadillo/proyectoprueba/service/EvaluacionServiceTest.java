package cl.duoc.ligranadillo.proyectoprueba.service;

import cl.duoc.ligranadillo.proyectoprueba.model.Evaluacion;
import cl.duoc.ligranadillo.proyectoprueba.repository.EvaluacionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EvaluacionServiceTest {

    private EvaluacionRepository evaluacionRepository;
    private EvaluacionService evaluacionService;

    @BeforeEach
    public void setUp() {
        evaluacionRepository = Mockito.mock(EvaluacionRepository.class);
        evaluacionService = new EvaluacionService(evaluacionRepository);
    }

    @Test
    public void testCrearEvaluacion() {
        Evaluacion evaluacion = new Evaluacion(null, "Prueba Java", "Cuestionario", 100, "1");
        Evaluacion evaluacionGuardada = new Evaluacion(1L, "Prueba Java", "Cuestionario", 100, "1");

        when(evaluacionRepository.save(evaluacion)).thenReturn(evaluacionGuardada);

        Evaluacion result = evaluacionService.guardarEvaluacion(evaluacion);

        assertNotNull(result.getId());
        assertEquals("Prueba Java", result.getTitulo());
        verify(evaluacionRepository, times(1)).save(evaluacion);
    }

    @Test
    public void testListarEvaluaciones() {
        List<Evaluacion> lista = Arrays.asList(
                new Evaluacion(1L, "Prueba Java", "Cuestionario", 100, "1"),
                new Evaluacion(2L, "Prueba Spring", "Tarea", 80, "1")
        );

        when(evaluacionRepository.findAll()).thenReturn(lista);

        List<Evaluacion> resultado = evaluacionService.obtenerEvaluaciones();

        assertFalse(resultado.isEmpty());
        assertEquals(2, resultado.size());
    }

    @Test
    public void testObtenerEvaluacionPorIdExistente() {
        Evaluacion evaluacion = new Evaluacion(1L, "Prueba Java", "Cuestionario", 100, "1");
        when(evaluacionRepository.findById(1L)).thenReturn(Optional.of(evaluacion));

        Optional<Evaluacion> resultado = evaluacionService.obtenerEvaluacionPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Prueba Java", resultado.get().getTitulo());
    }

    @Test
    public void testObtenerEvaluacionPorIdNoExistente() {
        when(evaluacionRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Evaluacion> resultado = evaluacionService.obtenerEvaluacionPorId(999L);

        assertTrue(resultado.isEmpty());
    }

    @Test
    public void testActualizarEvaluacionExistente() {
        Evaluacion existente = new Evaluacion(1L, "Prueba Java", "Cuestionario", 100, "1");
        Evaluacion actualizada = new Evaluacion(null, "Prueba Java Avanzada", "Cuestionario", 120, "1");

        when(evaluacionRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(evaluacionRepository.save(any(Evaluacion.class))).thenReturn(
                new Evaluacion(1L, "Prueba Java Avanzada", "Cuestionario", 120, "1")
        );

        Optional<Evaluacion> resultado = evaluacionService.actualizarEvaluacion(1L, actualizada);

        assertTrue(resultado.isPresent());
        assertEquals(120, resultado.get().getPuntajeMaximo());
        verify(evaluacionRepository, times(1)).save(any(Evaluacion.class));
    }

    @Test
    public void testActualizarEvaluacionNoExistente() {
        Evaluacion actualizada = new Evaluacion(null, "Prueba Inexistente", "Tarea", 50, "1");

        when(evaluacionRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Evaluacion> resultado = evaluacionService.actualizarEvaluacion(999L, actualizada);

        assertTrue(resultado.isEmpty());
        verify(evaluacionRepository, never()).save(any(Evaluacion.class));
    }

    @Test
    public void testEliminarEvaluacionExistente() {
        when(evaluacionRepository.existsById(1L)).thenReturn(true);

        boolean eliminado = evaluacionService.eliminarEvaluacion(1L);

        assertTrue(eliminado);
        verify(evaluacionRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testEliminarEvaluacionNoExistente() {
        when(evaluacionRepository.existsById(999L)).thenReturn(false);

        boolean eliminado = evaluacionService.eliminarEvaluacion(999L);

        assertFalse(eliminado);
        verify(evaluacionRepository, never()).deleteById(anyLong());
    }
}
