package cl.duoc.ligranadillo.proyectoprueba.service;

import cl.duoc.ligranadillo.proyectoprueba.model.Curso;
import cl.duoc.ligranadillo.proyectoprueba.repository.CursoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CursoServiceTest {

    private CursoRepository cursoRepository;
    private CursoService cursoService;

    @BeforeEach
    public void setup() {
        cursoRepository = mock(CursoRepository.class);
        cursoService = new CursoService(cursoRepository);
    }

    @Test
    public void testGuardarCurso() {
        Curso curso = new Curso(null, "Java Básico", "Intro a Java", "Programación", "2024-07-01", "2024-08-01", "Juan");
        Curso cursoConId = new Curso(1L, curso.getNombre(), curso.getDescripcion(), curso.getCategoria(), curso.getFechaInicio(), curso.getFechaFin(), curso.getInstructor());

        when(cursoRepository.save(curso)).thenReturn(cursoConId);

        Curso resultado = cursoService.guardarCurso(curso);

        assertNotNull(resultado.getId());
        assertEquals("Java Básico", resultado.getNombre());
        verify(cursoRepository).save(curso);
    }

    @Test
    public void testObtenerCursosDevuelveLista() {
        Curso curso = new Curso(1L, "Java Básico", "Intro", "Prog", "2024-07", "2024-08", "Juan");
        when(cursoRepository.findAll()).thenReturn(Collections.singletonList(curso));

        List<Curso> cursos = cursoService.obtenerCursos();

        assertFalse(cursos.isEmpty());
        assertEquals(1, cursos.size());
        verify(cursoRepository).findAll();
    }

    @Test
    public void testObtenerCursoPorIdExistente() {
        Curso curso = new Curso(1L, "Java Básico", "Intro", "Prog", "2024-07", "2024-08", "Juan");
        when(cursoRepository.findById(1L)).thenReturn(Optional.of(curso));

        Optional<Curso> resultado = cursoService.obtenerCursoPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Java Básico", resultado.get().getNombre());
        verify(cursoRepository).findById(1L);
    }

    @Test
    public void testObtenerCursoPorIdNoExistente() {
        when(cursoRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Curso> resultado = cursoService.obtenerCursoPorId(99L);

        assertFalse(resultado.isPresent());
        verify(cursoRepository).findById(99L);
    }

    @Test
    public void testActualizarCursoExistente() {
        Curso cursoExistente = new Curso(1L, "Java", "Intro", "Dev", "2024-01", "2024-02", "Juan");
        Curso cursoActualizado = new Curso(null, "Java", "Avanzado", "Dev", "2024-01", "2024-03", "Juan");

        when(cursoRepository.findById(1L)).thenReturn(Optional.of(cursoExistente));
        when(cursoRepository.save(any(Curso.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Optional<Curso> resultado = cursoService.actualizarCurso(1L, cursoActualizado);

        assertTrue(resultado.isPresent());
        assertEquals("Avanzado", resultado.get().getDescripcion());
        assertEquals("2024-03", resultado.get().getFechaFin());

        ArgumentCaptor<Curso> captor = ArgumentCaptor.forClass(Curso.class);
        verify(cursoRepository).save(captor.capture());
        assertEquals("Avanzado", captor.getValue().getDescripcion());
    }

    @Test
    public void testActualizarCursoNoExistente() {
        when(cursoRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Curso> resultado = cursoService.actualizarCurso(99L, new Curso());

        assertFalse(resultado.isPresent());
        verify(cursoRepository).findById(99L);
        verify(cursoRepository, never()).save(any());
    }

    @Test
    public void testEliminarCursoExistente() {
        when(cursoRepository.existsById(1L)).thenReturn(true);

        boolean eliminado = cursoService.eliminarCurso(1L);

        assertTrue(eliminado);
        verify(cursoRepository).deleteById(1L);
    }

    @Test
    public void testEliminarCursoNoExistente() {
        when(cursoRepository.existsById(99L)).thenReturn(false);

        boolean eliminado = cursoService.eliminarCurso(99L);

        assertFalse(eliminado);
        verify(cursoRepository, never()).deleteById(any());
    }
}
