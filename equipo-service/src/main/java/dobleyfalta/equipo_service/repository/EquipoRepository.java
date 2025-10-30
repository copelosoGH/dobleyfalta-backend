package dobleyfalta.equipo_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import dobleyfalta.equipo_service.models.Equipo;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Integer> {
    Equipo findByIdEquipo(Integer idEquipo);
}
