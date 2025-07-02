package cl.duoc.ligranadillo.proyectoprueba.service;

import cl.duoc.ligranadillo.proyectoprueba.model.Contenido;
import cl.duoc.ligranadillo.proyectoprueba.repository.ContenidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ContenidoServiceTest {

    private ContenidoRepository contenidoRepository;
    private ContenidoService contenidoService;

    @BeforeEach
    public void setUp() {
        contenidoRepository = Mockito.mock(ContenidoRepository.class);
        contenidoService = new ContenidoService(contenidoRepository);
    }

    @Test
    public void testCrearContenido() {
        Contenido contenido = new Contenido(null, "Video Java", "Video", "https://youtube.com/java", "Intro Java");
        Contenido guardado = new Contenido(1L, "Video Java", "Video", "https://youtube.com/java", "Intro Java");

        when(contenidoRepository.save(contenido)).thenReturn(guardado);

        Contenido result = contenidoService.guardarContenido(contenido);

        assertNotNull(result.getId());
        assertEquals("Video Java", result.getTitulo());
        verify(contenidoRepository, times(1)).save(contenido);
    }

    @Test
    public void testListarContenidos() {
        List<Contenido> lista = Arrays.asList(
                new Contenido(1L, "Video Java", "Video", "url1", "desc1"),
                new Contenido(2L, "PDF Spring", "Documento", "url2", "desc2")
        );

        when(contenidoRepository.findAll()).thenReturn(lista);

        List<Contenido> resultado = contenidoService.obtenerContenidos();

        assertFalse(resultado.isEmpty());
        assertEquals(2, resultado.size());
    }

    @Test
    public void testObtenerContenidoPorIdExistente() {
        Contenido contenido = new Contenido(1L, "Video Java", "Video", "url", "desc");
        when(contenidoRepository.findById(1L)).thenReturn(Optional.of(contenido));

        Optional<Contenido> resultado = contenidoService.obtenerContenidoPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Video Java", resultado.get().getTitulo());
    }

    @Test
    public void testObtenerContenidoPorIdNoExistente() {
        when(contenidoRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Contenido> resultado = contenidoService.obtenerContenidoPorId(999L);

        assertTrue(resultado.isEmpty());
    }

    @Test
    public void testActualizarContenidoExistente() {
        Contenido existente = new Contenido(1L, "Video Java", "Video", "url", "desc");
        Contenido actualizado = new Contenido(null, "Video Java Avanzado", "Video", "url2", "desc2");

        when(contenidoRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(contenidoRepository.save(any(Contenido.class))).thenReturn(
                new Contenido(1L, "Video Java Avanzado", "Video", "url2", "desc2")
        );

        Optional<Contenido> resultado = contenidoService.actualizarContenido(1L, actualizado);

        assertTrue(resultado.isPresent());
        assertEquals("Video Java Avanzado", resultado.get().getTitulo());
    }

    @Test
    public void testActualizarContenidoNoExistente() {
        Contenido actualizado = new Contenido(null, "Video", "Video", "url", "desc");

        when(contenidoRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Contenido> resultado = contenidoService.actualizarContenido(999L, actualizado);

        assertTrue(resultado.isEmpty());
        verify(contenidoRepository, never()).save(any(Contenido.class));
    }

    @Test
    public void testEliminarContenidoExistente() {
        when(contenidoRepository.existsById(1L)).thenReturn(true);

        boolean eliminado = contenidoService.eliminarContenido(1L);

        assertTrue(eliminado);
        verify(contenidoRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testEliminarContenidoNoExistente() {
        when(contenidoRepository.existsById(999L)).thenReturn(false);

        boolean eliminado = contenidoService.eliminarContenido(999L);

        assertFalse(eliminado);
        verify(contenidoRepository, never()).deleteById(anyLong());
    }
}
