package cl.duoc.ligranadillo.proyectoprueba.controller;

import cl.duoc.ligranadillo.proyectoprueba.model.User;
import cl.duoc.ligranadillo.proyectoprueba.controller.request.LoginRequest;
import cl.duoc.ligranadillo.proyectoprueba.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v2/users")
@Tag(name = "Usuarios", description = "Microservicio para gestión de usuarios y autenticación")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/crear")
    @Operation(summary = "Crear usuario", description = "Registra un nuevo usuario")
    public ResponseEntity<Map<String, Object>> crearUsuario(@RequestBody User user) {
        User creado = userService.guardarUser(user);
        return ResponseEntity.status(201).body(Map.of(
                "message", "Usuario creado exitosamente",
                "user", creado
        ));
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar usuarios", description = "Obtiene todos los usuarios")
    public ResponseEntity<Map<String, Object>> listarUsuarios() {
        List<User> lista = userService.obtenerUsers();
        return ResponseEntity.ok(Map.of(
                "message", "Usuarios obtenidos correctamente",
                "total", lista.size(),
                "data", lista
        ));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario por ID", description = "Obtiene un usuario por su ID")
    public ResponseEntity<Map<String, Object>> obtenerUsuario(@PathVariable Long id) {
        return userService.obtenerUserPorId(id)
                .map(u -> ResponseEntity.ok(Map.of("message", "Usuario encontrado", "user", u)))
                .orElse(ResponseEntity.status(404).body(Map.of("message", "Usuario con ID " + id + " no encontrado")));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar usuario", description = "Actualiza un usuario existente")
    public ResponseEntity<Map<String, Object>> actualizarUsuario(@PathVariable Long id, @RequestBody User user) {
        return userService.actualizarUser(id, user)
                .map(u -> ResponseEntity.ok(Map.of("message", "Usuario actualizado correctamente", "user", u)))
                .orElse(ResponseEntity.status(404).body(Map.of("message", "No se pudo actualizar: usuario con ID " + id + " no encontrado")));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario existente")
    public ResponseEntity<Map<String, Object>> eliminarUsuario(@PathVariable Long id) {
        boolean eliminado = userService.eliminarUser(id);
        return eliminado
                ? ResponseEntity.ok(Map.of("message", "Usuario eliminado correctamente"))
                : ResponseEntity.status(404).body(Map.of("message", "Usuario con ID " + id + " no encontrado"));
    }

    @PostMapping("/login")
    @Operation(summary = "Login de usuario", description = "Autentica un usuario por email y password")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest loginRequest) {
        Optional<User> userOpt = userService.login(loginRequest.getEmail(), loginRequest.getPassword());
        return userOpt
                .map(u -> ResponseEntity.ok(Map.of(
                        "message", "Usuario logueado correctamente",
                        "user", u)))
                .orElse(ResponseEntity.status(401).body(Map.of(
                        "message", "Credenciales inválidas o usuario no encontrado"
                )));
    }
}
