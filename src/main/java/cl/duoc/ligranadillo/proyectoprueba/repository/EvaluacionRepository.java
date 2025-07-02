package cl.duoc.ligranadillo.proyectoprueba.repository;

import cl.duoc.ligranadillo.proyectoprueba.model.Evaluacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluacionRepository extends JpaRepository<Evaluacion, Long> {
}
