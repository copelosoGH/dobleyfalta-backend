package dobleyfalta.partidos_services.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import dobleyfalta.partidos_services.models.Partido;
import jakarta.transaction.Transactional;


@Repository
public interface PartidoRespository extends JpaRepository<Partido, Integer>{
    
    Partido findByIdPartido(Integer idPartido);

    List<Partido> findAll();

    List<Partido> findByJornadaIdJornada(Integer idJornada);

    // MÉTODO NUEVO para actualizar puntos locales de forma atómica
    @Transactional
    @Modifying
    @Query("UPDATE Partido p SET p.puntosLocal = :puntosLocal WHERE p.idPartido = :idPartido")
    int updatePuntosLocal(Integer idPartido, Integer puntosLocal);

    // MÉTODO NUEVO para actualizar puntos visitantes de forma atómica
    @Transactional
    @Modifying
    @Query("UPDATE Partido p SET p.puntosVisitante = :puntosVisitante WHERE p.idPartido = :idPartido")
    int updatePuntosVisitante(Integer idPartido, Integer puntosVisitante);
    
    List<Partido> findByJornada_Liga_IdLiga(Integer idLiga);
}
