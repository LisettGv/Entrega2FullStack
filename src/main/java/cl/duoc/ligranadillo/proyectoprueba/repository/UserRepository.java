package cl.duoc.ligranadillo.proyectoprueba.repository;

import cl.duoc.ligranadillo.proyectoprueba.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
