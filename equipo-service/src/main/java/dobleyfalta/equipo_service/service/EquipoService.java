package dobleyfalta.equipo_service.service;

import java.util.List;
import org.springframework.stereotype.Service;
import dobleyfalta.equipo_service.models.Equipo;
import dobleyfalta.equipo_service.repository.EquipoRepository;

@Service
public class EquipoService {
    
    private final EquipoRepository equipoRepository;

    public EquipoService(EquipoRepository equipoRepository) {
        this.equipoRepository = equipoRepository;
    }

    // 🔹 Obtener todos los equipos
    public List<Equipo> getEquiposAll() {
        return equipoRepository.findAll();
    }

    // 🔹 Obtener equipo por id
    public Equipo getEquipoById(Integer id) {
        return equipoRepository.findByIdEquipo(id);
    }

    // 🔹 Crear equipo
    public Equipo crearEquipo(Equipo equipo) {
        return equipoRepository.save(equipo);
    }

    // 🔹 Editar equipo
    public Equipo editarEquipo(Integer id, Equipo equipo) {
        Equipo equipoEdit = equipoRepository.findByIdEquipo(id);
        if (equipoEdit != null) {
            equipoEdit.setNombre(equipo.getNombre());
            equipoEdit.setCiudad(equipo.getCiudad());
            equipoEdit.setDireccion(equipo.getDireccion());
            equipoEdit.setLogo(equipo.getLogo());
            equipoEdit.setDescripcion(equipo.getDescripcion());
            equipoEdit.setIdLiga(equipo.getIdLiga());
            return equipoRepository.save(equipoEdit);
        }
        return null;
    }

    // 🔹 Eliminar equipo
    public Equipo eliminarEquipo(Integer id) {
        Equipo equipo = equipoRepository.findByIdEquipo(id);
        if (equipo != null) {
            equipoRepository.delete(equipo);
            return equipo;
        }
        return null;
    }
}
