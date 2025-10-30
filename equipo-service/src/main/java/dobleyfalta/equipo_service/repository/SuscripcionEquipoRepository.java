package dobleyfalta.equipo_service.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import dobleyfalta.equipo_service.models.SuscripcionEquipo;
import dobleyfalta.equipo_service.models.SuscripcionEquipoId;

@Repository
public interface SuscripcionEquipoRepository extends JpaRepository<SuscripcionEquipo, SuscripcionEquipoId> {

    List<SuscripcionEquipo> findByIdIdUsuario(Integer idUsuario);
    List<SuscripcionEquipo> findByIdIdEquipo(Integer idEquipo);
}
