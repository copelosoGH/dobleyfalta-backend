package dobleyfalta.partidos_services.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import dobleyfalta.partidos_services.models.Jornada;

@Repository
public interface JornadaRepository extends JpaRepository<Jornada, Integer> {
    Jornada findByIdJornada(Integer idJornada);
}
