package cl.duoc.ligranadillo.proyectoprueba.controller;

import cl.duoc.ligranadillo.proyectoprueba.model.Curso;
import cl.duoc.ligranadillo.proyectoprueba.repository.CursoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CursoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        cursoRepository.deleteAll();
    }

    @Test
    public void testCrearCurso() throws Exception {
        Curso curso = new Curso(null, "Spring Boot", "Curso avanzado", "Programación",
                "2025-08-01", "2025-10-01", "Juan");

        mockMvc.perform(post("/api/v2/cursos/crear")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(curso)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Curso creado exitosamente"))
                .andExpect(jsonPath("$.curso.nombre").value("Spring Boot"));
    }

    @Test
    public void testListarCursos() throws Exception {
        cursoRepository.save(new Curso(null, "Java", "Intro Java", "Programación",
                "2025-08-01", "2025-10-01", "Ana"));
        cursoRepository.save(new Curso(null, "Python", "Intro Python", "Programación",
                "2025-09-01", "2025-11-01", "Carlos"));

        mockMvc.perform(get("/api/v2/cursos/listar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Cursos obtenidos correctamente"))
                .andExpect(jsonPath("$.total").value(2))
                .andExpect(jsonPath("$.data", hasSize(2)));
    }

    @Test
    public void testObtenerCurso() throws Exception {
        Curso curso = cursoRepository.save(new Curso(null, "Spring", "Spring desc", "Programación",
                "2025-08-01", "2025-10-01", "Juan"));

        mockMvc.perform(get("/api/v2/cursos/" + curso.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Curso encontrado"))
                .andExpect(jsonPath("$.curso.nombre").value("Spring"));
    }

    @Test
    public void testObtenerCursoNoExistente() throws Exception {
        mockMvc.perform(get("/api/v2/cursos/9999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Curso con ID 9999 no encontrado"));
    }

    @Test
    public void testActualizarCurso() throws Exception {
        Curso curso = cursoRepository.save(new Curso(null, "Java", "desc", "Programación",
                "2025-08-01", "2025-10-01", "Juan"));

        Curso actualizado = new Curso(null, "Java Avanzado", "desc avanzada", "Programación",
                "2025-08-01", "2025-12-01", "Juan");

        mockMvc.perform(put("/api/v2/cursos/" + curso.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Curso actualizado correctamente"))
                .andExpect(jsonPath("$.curso.nombre").value("Java Avanzado"));
    }

    @Test
    public void testActualizarCursoNoExistente() throws Exception {
        Curso actualizado = new Curso(null, "No existe", "desc", "Programación",
                "2025-08-01", "2025-12-01", "Juan");

        mockMvc.perform(put("/api/v2/cursos/9999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("No se pudo actualizar: curso con ID 9999 no encontrado"));
    }

    @Test
    public void testEliminarCurso() throws Exception {
        Curso curso = cursoRepository.save(new Curso(null, "Python", "desc", "Programación",
                "2025-08-01", "2025-10-01", "Ana"));

        mockMvc.perform(delete("/api/v2/cursos/" + curso.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Curso eliminado correctamente"));
    }

    @Test
    public void testEliminarCursoNoExistente() throws Exception {
        mockMvc.perform(delete("/api/v2/cursos/9999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Curso con ID 9999 no encontrado"));
    }
}
