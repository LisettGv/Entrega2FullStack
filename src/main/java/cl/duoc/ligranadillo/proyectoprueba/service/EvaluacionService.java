package cl.duoc.ligranadillo.proyectoprueba.service;

import cl.duoc.ligranadillo.proyectoprueba.model.Evaluacion;
import cl.duoc.ligranadillo.proyectoprueba.repository.EvaluacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EvaluacionService {

    private final EvaluacionRepository evaluacionRepository;

    @Autowired
    public EvaluacionService(EvaluacionRepository evaluacionRepository) {
        this.evaluacionRepository = evaluacionRepository;
    }

    public Evaluacion guardarEvaluacion(Evaluacion evaluacion) {
        return evaluacionRepository.save(evaluacion);
    }

    public List<Evaluacion> obtenerEvaluaciones() {
        return evaluacionRepository.findAll();
    }

    public Optional<Evaluacion> obtenerEvaluacionPorId(Long id) {
        return evaluacionRepository.findById(id);
    }

    public Optional<Evaluacion> actualizarEvaluacion(Long id, Evaluacion evaluacionActualizada) {
        return evaluacionRepository.findById(id).map(evaluacionExistente -> {
            evaluacionExistente.setTitulo(evaluacionActualizada.getTitulo());
            evaluacionExistente.setTipo(evaluacionActualizada.getTipo());
            evaluacionExistente.setPuntajeMaximo(evaluacionActualizada.getPuntajeMaximo());
            evaluacionExistente.setCursoId(evaluacionActualizada.getCursoId());
            return evaluacionRepository.save(evaluacionExistente);
        });
    }

    public boolean eliminarEvaluacion(Long id) {
        if (evaluacionRepository.existsById(id)) {
            evaluacionRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
