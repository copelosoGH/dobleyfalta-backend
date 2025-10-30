package dobleyfalta.partidos_services.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import dobleyfalta.partidos_services.models.Liga;

@Repository
public interface LigaRepository extends JpaRepository<Liga, Integer> {
    Liga findByIdLiga(Integer idLiga);
}
