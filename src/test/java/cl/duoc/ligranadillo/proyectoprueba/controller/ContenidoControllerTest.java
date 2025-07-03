package cl.duoc.ligranadillo.proyectoprueba.controller;

import cl.duoc.ligranadillo.proyectoprueba.model.Contenido;
import cl.duoc.ligranadillo.proyectoprueba.service.ContenidoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ContenidoController.class)
public class ContenidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContenidoService contenidoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCrearContenido() throws Exception {
        Contenido contenido = new Contenido(1L, "Intro Java", "Video", "https://url.com", "Basico");
        Mockito.when(contenidoService.guardarContenido(any(Contenido.class))).thenReturn(contenido);

        mockMvc.perform(post("/api/v2/contenidos/crear")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contenido)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Contenido creado exitosamente"))
                .andExpect(jsonPath("$.contenido.titulo").value("Intro Java"));
    }

    @Test
    void testListarContenidos() throws Exception {
        List<Contenido> lista = List.of(
                new Contenido(1L, "Intro Java", "Video", "https://url.com", "Basico"),
                new Contenido(2L, "Spring Boot", "Doc", "https://url.com/2", "Avanzado")
        );
        Mockito.when(contenidoService.obtenerContenidos()).thenReturn(lista);

        mockMvc.perform(get("/api/v2/contenidos/listar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").value(2))
                .andExpect(jsonPath("$.message").value("Contenidos obtenidos correctamente"));
    }

    @Test
    void testObtenerContenidoPorIdEncontrado() throws Exception {
        Contenido contenido = new Contenido(1L, "Intro Java", "Video", "https://url.com", "Basico");
        Mockito.when(contenidoService.obtenerContenidoPorId(1L)).thenReturn(Optional.of(contenido));

        mockMvc.perform(get("/api/v2/contenidos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Contenido encontrado"))
                .andExpect(jsonPath("$.contenido.titulo").value("Intro Java"));
    }

    @Test
    void testObtenerContenidoPorIdNoEncontrado() throws Exception {
        Mockito.when(contenidoService.obtenerContenidoPorId(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v2/contenidos/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Contenido con ID 1 no encontrado"));
    }

    @Test
    void testActualizarContenidoExiste() throws Exception {
        Contenido actualizado = new Contenido(1L, "Java Avanzado", "Video", "https://url2.com", "Avanzado");
        Mockito.when(contenidoService.actualizarContenido(eq(1L), any(Contenido.class)))
                .thenReturn(Optional.of(actualizado));

        mockMvc.perform(put("/api/v2/contenidos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Contenido actualizado correctamente"))
                .andExpect(jsonPath("$.contenido.titulo").value("Java Avanzado"));
    }

    @Test
    void testActualizarContenidoNoExiste() throws Exception {
        Contenido contenido = new Contenido(null, "Java Avanzado", "Video", "https://url2.com", "Avanzado");
        Mockito.when(contenidoService.actualizarContenido(eq(1L), any(Contenido.class)))
                .thenReturn(Optional.empty());

        mockMvc.perform(put("/api/v2/contenidos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contenido)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("No se pudo actualizar: contenido con ID 1 no encontrado"));
    }

    @Test
    void testEliminarContenidoExiste() throws Exception {
        Mockito.when(contenidoService.eliminarContenido(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/v2/contenidos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Contenido eliminado correctamente"));
    }

    @Test
    void testEliminarContenidoNoExiste() throws Exception {
        Mockito.when(contenidoService.eliminarContenido(1L)).thenReturn(false);

        mockMvc.perform(delete("/api/v2/contenidos/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Contenido con ID 1 no encontrado"));
    }
}
