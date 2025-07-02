package cl.duoc.ligranadillo.proyectoprueba.service;

import cl.duoc.ligranadillo.proyectoprueba.model.User;
import cl.duoc.ligranadillo.proyectoprueba.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User guardarUser(User user) {
        return userRepository.save(user);
    }

    public List<User> obtenerUsers() {
        return userRepository.findAll();
    }

    public Optional<User> obtenerUserPorId(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> actualizarUser(Long id, User userActualizado) {
        return userRepository.findById(id).map(userExistente -> {
            userExistente.setNombre(userActualizado.getNombre());
            userExistente.setPassword(userActualizado.getPassword());
            userExistente.setEmail(userActualizado.getEmail());
            userExistente.setValidated(userActualizado.isValidated());
            return userRepository.save(userExistente);
        });
    }


    public boolean eliminarUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<User> login(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
            return userOpt;
        }
        return Optional.empty();
    }

}
