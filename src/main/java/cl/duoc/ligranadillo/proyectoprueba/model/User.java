package cl.duoc.ligranadillo.proyectoprueba.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Schema(description = "Usuario registrado en el sistema")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del usuario", example = "1")
    private Long id;

    @Schema(description = "Nombre completo del usuario", example = "Juan Pérez")
    private String nombre;

    @Schema(description = "Nombre de usuario para el login", example = "juanito")
    private String username;

    @Schema(description = "Contraseña del usuario en texto plano (solo para pruebas)", example = "pass123")
    private String password;

    @Schema(description = "Correo electrónico del usuario", example = "juanito@example.com")
    private String email;

    @Schema(description = "Indica si el usuario ya validó su cuenta", example = "false")
    private boolean validated;
}
