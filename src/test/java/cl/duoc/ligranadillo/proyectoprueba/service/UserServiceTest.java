package cl.duoc.ligranadillo.proyectoprueba.service;

import cl.duoc.ligranadillo.proyectoprueba.model.User;
import cl.duoc.ligranadillo.proyectoprueba.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class UserServiceTest {

    @Test
    public void testGuardarUsuario() {
        UserRepository repoMock = Mockito.mock(UserRepository.class);
        UserService service = new UserService(repoMock);

        User user = new User(null, "Juan Pérez", "juanito", "pass123", "juanito@mail.com", false);
        User userGuardado = new User(1L, "Juan Pérez", "juanito", "pass123", "juanito@mail.com", false);

        Mockito.when(repoMock.save(user)).thenReturn(userGuardado);

        User result = service.guardarUser(user);

        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals("juanito", result.getUsername());
    }

    @Test
    public void testObtenerUsuarios() {
        UserRepository repoMock = Mockito.mock(UserRepository.class);
        UserService service = new UserService(repoMock);

        List<User> users = Arrays.asList(
                new User(1L, "Juan Pérez","juanito", "pass123", "juanito@mail.com", false),
                new User(2L, "Juan Pérez","ana", "abcd", "ana@mail.com", true)
        );
        Mockito.when(repoMock.findAll()).thenReturn(users);

        List<User> result = service.obtenerUsers();

        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void testObtenerUsuarioPorIdExiste() {
        UserRepository repoMock = Mockito.mock(UserRepository.class);
        UserService service = new UserService(repoMock);

        User user = new User(1L, "Juan Pérez","juanito", "pass123", "juanito@mail.com", false);
        Mockito.when(repoMock.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> result = service.obtenerUserPorId(1L);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("juanito", result.get().getUsername());
    }

    @Test
    public void testObtenerUsuarioPorIdNoExiste() {
        UserRepository repoMock = Mockito.mock(UserRepository.class);
        UserService service = new UserService(repoMock);

        Mockito.when(repoMock.findById(99L)).thenReturn(Optional.empty());

        Optional<User> result = service.obtenerUserPorId(99L);

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testActualizarUsuarioExiste() {
        UserRepository repoMock = Mockito.mock(UserRepository.class);
        UserService service = new UserService(repoMock);

        User userExistente = new User(1L, "Juan Pérez","juanito", "pass123", "juanito@mail.com", false);
        User userActualizado = new User(null, "Juan Gonzalez","juanito", "newpass", "juanito2@mail.com", true);

        Mockito.when(repoMock.findById(1L)).thenReturn(Optional.of(userExistente));
        Mockito.when(repoMock.save(Mockito.any(User.class))).thenAnswer(i -> i.getArguments()[0]);

        Optional<User> result = service.actualizarUser(1L, userActualizado);

        Assertions.assertEquals("juanito", result.get().getUsername());
        Assertions.assertEquals("newpass", result.get().getPassword());
        Assertions.assertEquals("juanito2@mail.com", result.get().getEmail());
        Assertions.assertTrue(result.get().isValidated());

    }

    @Test
    public void testActualizarUsuarioNoExiste() {
        UserRepository repoMock = Mockito.mock(UserRepository.class);
        UserService service = new UserService(repoMock);

        Mockito.when(repoMock.findById(99L)).thenReturn(Optional.empty());

        Optional<User> result = service.actualizarUser(99L, new User());

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testEliminarUsuarioExiste() {
        UserRepository repoMock = Mockito.mock(UserRepository.class);
        UserService service = new UserService(repoMock);

        Mockito.when(repoMock.existsById(1L)).thenReturn(true);

        boolean eliminado = service.eliminarUser(1L);

        Assertions.assertTrue(eliminado);
        Mockito.verify(repoMock).deleteById(1L);
    }

    @Test
    public void testEliminarUsuarioNoExiste() {
        UserRepository repoMock = Mockito.mock(UserRepository.class);
        UserService service = new UserService(repoMock);

        Mockito.when(repoMock.existsById(99L)).thenReturn(false);

        boolean eliminado = service.eliminarUser(99L);

        Assertions.assertFalse(eliminado);
        Mockito.verify(repoMock, Mockito.never()).deleteById(99L);
    }

    @Test
    public void testLoginExitoso() {
        UserRepository repoMock = Mockito.mock(UserRepository.class);
        UserService service = new UserService(repoMock);

        User user = new User(1L, "Juan Pérez","juanito", "pass123", "juanito@mail.com", false);
        Mockito.when(repoMock.findByEmail("juanito@mail.com")).thenReturn(Optional.of(user));

        Optional<User> result = service.login("juanito@mail.com", "pass123");

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("juanito", result.get().getUsername());
    }

    @Test
    public void testLoginFallidoPorPasswordIncorrecta() {
        UserRepository repoMock = Mockito.mock(UserRepository.class);
        UserService service = new UserService(repoMock);

        User user = new User(2L, "Ana Pérez","ana", "abcd", "ana@mail.com", true);
        Mockito.when(repoMock.findByEmail("ana@mail.com")).thenReturn(Optional.of(user));

        Optional<User> result = service.login("ana@mail.com", "wrong");

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testLoginFallidoUsuarioNoExiste() {
        UserRepository repoMock = Mockito.mock(UserRepository.class);
        UserService service = new UserService(repoMock);

        Mockito.when(repoMock.findByEmail("noexiste@mail.com")).thenReturn(Optional.empty());

        Optional<User> result = service.login("noexiste@mail.com", "any");

        Assertions.assertTrue(result.isEmpty());
    }
}
