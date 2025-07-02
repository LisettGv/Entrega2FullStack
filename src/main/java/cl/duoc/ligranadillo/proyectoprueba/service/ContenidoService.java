package cl.duoc.ligranadillo.proyectoprueba.service;

import cl.duoc.ligranadillo.proyectoprueba.model.Contenido;
import cl.duoc.ligranadillo.proyectoprueba.repository.ContenidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContenidoService {

    private final ContenidoRepository contenidoRepository;

    @Autowired
    public ContenidoService(ContenidoRepository contenidoRepository) {
        this.contenidoRepository = contenidoRepository;
    }

    public Contenido guardarContenido(Contenido contenido) {
        return contenidoRepository.save(contenido);
    }

    public List<Contenido> obtenerContenidos() {
        return contenidoRepository.findAll();
    }

    public Optional<Contenido> obtenerContenidoPorId(Long id) {
        return contenidoRepository.findById(id);
    }

    public Optional<Contenido> actualizarContenido(Long id, Contenido contenidoActualizado) {
        return contenidoRepository.findById(id).map(contenidoExistente -> {
            contenidoExistente.setTitulo(contenidoActualizado.getTitulo());
            contenidoExistente.setTipo(contenidoActualizado.getTipo());
            contenidoExistente.setUrl(contenidoActualizado.getUrl());
            contenidoExistente.setDescripcion(contenidoActualizado.getDescripcion());
            return contenidoRepository.save(contenidoExistente);
        });
    }

    public boolean eliminarContenido(Long id) {
        if (contenidoRepository.existsById(id)) {
            contenidoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

