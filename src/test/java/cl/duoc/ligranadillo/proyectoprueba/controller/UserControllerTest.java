package cl.duoc.ligranadillo.proyectoprueba.controller;

import cl.duoc.ligranadillo.proyectoprueba.model.User;
import cl.duoc.ligranadillo.proyectoprueba.service.UserService;
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

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCrearUsuario() throws Exception {
        User user = new User(null, "Juan Pérez", "juanito", "pass123", "juan@mail.com", false);
        User userGuardado = new User(1L, "Juan Pérez", "juanito", "pass123", "juan@mail.com", false);

        Mockito.when(userService.guardarUser(Mockito.any(User.class))).thenReturn(userGuardado);

        mockMvc.perform(post("/api/v2/users/crear")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message", is("Usuario creado exitosamente")))
                .andExpect(jsonPath("$.user.id", is(1)));
    }

    @Test
    void testListarUsuarios() throws Exception {
        List<User> lista = List.of(
                new User(1L, "Juan Pérez", "juanito", "pass123", "juan@mail.com", false),
                new User(2L, "Ana Ruiz", "anar", "pass456", "ana@mail.com", true)
        );

        Mockito.when(userService.obtenerUsers()).thenReturn(lista);

        mockMvc.perform(get("/api/v2/users/listar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Usuarios obtenidos correctamente")))
                .andExpect(jsonPath("$.total", is(2)))
                .andExpect(jsonPath("$.data", hasSize(2)));
    }

    @Test
    void testObtenerUsuarioPorId() throws Exception {
        User user = new User(1L, "Juan Pérez", "juanito", "pass123", "juan@mail.com", false);

        Mockito.when(userService.obtenerUserPorId(1L)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/api/v2/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Usuario encontrado")))
                .andExpect(jsonPath("$.user.id", is(1)));
    }

    @Test
    void testActualizarUsuario() throws Exception {
        User user = new User(null, "Juan Actualizado", "juanito2", "newpass", "juanito2@mail.com", true);
        User actualizado = new User(1L, "Juan Actualizado", "juanito2", "newpass", "juanito2@mail.com", true);

        Mockito.when(userService.actualizarUser(Mockito.eq(1L), Mockito.any(User.class)))
                .thenReturn(Optional.of(actualizado));

        mockMvc.perform(put("/api/v2/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Usuario actualizado correctamente")))
                .andExpect(jsonPath("$.user.id", is(1)))
                .andExpect(jsonPath("$.user.username", is("juanito2")));
    }

    @Test
    void testEliminarUsuario() throws Exception {
        Mockito.when(userService.eliminarUser(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/v2/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Usuario eliminado correctamente")));
    }

    @Test
    void testLoginExitoso() throws Exception {
        User user = new User(1L, "Juan Pérez", "juanito", "pass123", "juan@mail.com", true);

        Mockito.when(userService.login("juan@mail.com", "pass123")).thenReturn(Optional.of(user));

        String loginJson = """
                {
                  "email": "juan@mail.com",
                  "password": "pass123"
                }
                """;

        mockMvc.perform(post("/api/v2/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Usuario logueado correctamente")))
                .andExpect(jsonPath("$.user.id", is(1)));
    }

    @Test
    void testLoginFallido() throws Exception {
        Mockito.when(userService.login("juan@mail.com", "wrong")).thenReturn(Optional.empty());

        String loginJson = """
                {
                  "email": "juan@mail.com",
                  "password": "wrong"
                }
                """;

        mockMvc.perform(post("/api/v2/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message", is("Credenciales inválidas o usuario no encontrado")));
    }
}
