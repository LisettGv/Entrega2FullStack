package cl.duoc.ligranadillo.proyectoprueba.service;

import cl.duoc.ligranadillo.proyectoprueba.model.Curso;
import cl.duoc.ligranadillo.proyectoprueba.repository.CursoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;

    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    public Curso guardarCurso(Curso curso) {
        return cursoRepository.save(curso);
    }

    public List<Curso> obtenerCursos() {
        return cursoRepository.findAll();
    }

    public Optional<Curso> obtenerCursoPorId(Long id) {
        return cursoRepository.findById(id);
    }

    public Optional<Curso> actualizarCurso(Long id, Curso cursoActualizado) {
        return cursoRepository.findById(id).map(cursoExistente -> {
            cursoExistente.setNombre(cursoActualizado.getNombre());
            cursoExistente.setDescripcion(cursoActualizado.getDescripcion());
            cursoExistente.setCategoria(cursoActualizado.getCategoria());
            cursoExistente.setFechaInicio(cursoActualizado.getFechaInicio());
            cursoExistente.setFechaFin(cursoActualizado.getFechaFin());
            cursoExistente.setInstructor(cursoActualizado.getInstructor());
            return cursoRepository.save(cursoExistente);
        });
    }

    public boolean eliminarCurso(Long id) {
        if (cursoRepository.existsById(id)) {
            cursoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
