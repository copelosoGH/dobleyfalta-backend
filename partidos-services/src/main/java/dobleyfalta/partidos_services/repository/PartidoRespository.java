package dobleyfalta.partidos_services.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dobleyfalta.partidos_services.models.Partido;


@Repository
public interface PartidoRespository extends JpaRepository<Partido, Integer>{
    
    Partido findByIdPartido(Integer idPartido);

    List<Partido> findAll();

    List<Partido> findByJornadaIdJornada(Integer idJornada);
}
