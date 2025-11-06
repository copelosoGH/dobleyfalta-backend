package dobleyfalta.equipo_service.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Service;
import dobleyfalta.equipo_service.models.Equipo;
import dobleyfalta.equipo_service.repository.EquipoRepository;

@Service
public class EquipoService {

    private final EquipoRepository equipoRepository;

    private final ImagenService imagenService;

    public EquipoService(EquipoRepository equipoRepository, ImagenService imagenService) {
        this.equipoRepository = equipoRepository;
        this.imagenService = imagenService;
    }

    // Obtener todos los equipos
    public List<Equipo> getEquiposAll() {
        return equipoRepository.findAll();
    }

    // Obtener equipo por id
    public Equipo getEquipoById(Integer id) {
        return equipoRepository.findByIdEquipo(id);
    }

    public Equipo crearEquipo(Equipo equipo) {
        try {
            if (equipo.getLogo() != null && !equipo.getLogo().isEmpty()) {
                String nombreArchivo = "equipo_" + System.currentTimeMillis() + ".jpg";
                String rutaGuardada = imagenService.guardarImagenBase64(equipo.getLogo(), nombreArchivo);
                equipo.setLogo(rutaGuardada);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar el logo: " + e.getMessage());
        }
        return equipoRepository.save(equipo);
    }

    public Equipo editarEquipo(Integer id, Equipo equipo) {
        Equipo equipoEdit = equipoRepository.findByIdEquipo(id);
        if (equipoEdit != null) {
            if (equipo.getNombre() != null)
                equipoEdit.setNombre(equipo.getNombre());
            if (equipo.getCiudad() != null)
                equipoEdit.setCiudad(equipo.getCiudad());
            if (equipo.getDireccion() != null)
                equipoEdit.setDireccion(equipo.getDireccion());
            if (equipo.getDescripcion() != null)
                equipoEdit.setDescripcion(equipo.getDescripcion());
            if (equipo.getIdLiga() != null)
                equipoEdit.setIdLiga(equipo.getIdLiga());

            try {
                if (equipo.getLogo() != null && !equipo.getLogo().isEmpty()) {
                    // eliminar logo viejo
                    if (equipoEdit.getLogo() != null && !equipoEdit.getLogo().isEmpty()) {
                        File viejo = new File(equipoEdit.getLogo());
                        if (viejo.exists())
                            viejo.delete();
                    }

                    String nombreArchivo = "equipo_" + System.currentTimeMillis() + ".jpg";
                    String rutaGuardada = imagenService.guardarImagenBase64(equipo.getLogo(), nombreArchivo);
                    equipoEdit.setLogo(rutaGuardada);
                }
            } catch (IOException e) {
                throw new RuntimeException("Error al actualizar el logo: " + e.getMessage());
            }

            return equipoRepository.save(equipoEdit);
        }
        return null;
    }

    public Equipo eliminarEquipo(Integer id) {
        Equipo equipo = equipoRepository.findByIdEquipo(id);
        if (equipo != null) {
            if (equipo.getLogo() != null && !equipo.getLogo().isEmpty()) {
                File archivo = new File(equipo.getLogo());
                if (archivo.exists()) archivo.delete();
            }
            equipoRepository.delete(equipo);
            return equipo;
        } 
        return null;
    }
}
