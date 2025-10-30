package dobleyfalta.equipo_service.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import dobleyfalta.equipo_service.DTO.EquipoDTO;
import dobleyfalta.equipo_service.DTO.SuscripcionEquipoDTO;
import dobleyfalta.equipo_service.DTO.UsuarioDTO;
import dobleyfalta.equipo_service.models.SuscripcionEquipo;
import dobleyfalta.equipo_service.models.SuscripcionEquipoId;
import dobleyfalta.equipo_service.repository.SuscripcionEquipoRepository;

@Service
public class SuscripcionEquipoService {

    private final SuscripcionEquipoRepository suscripcionRepository;
    private final RestTemplate restTemplate;

    public SuscripcionEquipoService(SuscripcionEquipoRepository suscripcionRepository) {
        this.suscripcionRepository = suscripcionRepository;
        this.restTemplate = new RestTemplate();
    }

    public List<SuscripcionEquipoDTO> getAll() {
        return suscripcionRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public List<SuscripcionEquipoDTO> getByUsuario(Integer idUsuario) {
        return suscripcionRepository.findByIdIdUsuario(idUsuario).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public List<SuscripcionEquipoDTO> getByEquipo(Integer idEquipo) {
        return suscripcionRepository.findByIdIdEquipo(idEquipo).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public SuscripcionEquipo suscribirse(Integer idUsuario, Integer idEquipo) {
        SuscripcionEquipoId id = new SuscripcionEquipoId(idUsuario, idEquipo);
        if (suscripcionRepository.existsById(id)) {
            return null;
        }

        SuscripcionEquipo nueva = new SuscripcionEquipo();
        nueva.setId(id);
        nueva.setFechaSuscripcion(new Date(System.currentTimeMillis()));

        return suscripcionRepository.save(nueva);
    }

    public void eliminarSuscripcion(Integer idUsuario, Integer idEquipo) {
        SuscripcionEquipoId id = new SuscripcionEquipoId(idUsuario, idEquipo);
        suscripcionRepository.deleteById(id);
    }

    private SuscripcionEquipoDTO convertirADTO(SuscripcionEquipo suscripcion) {
        SuscripcionEquipoDTO dto = new SuscripcionEquipoDTO();
        dto.setIdUsuario(suscripcion.getId().getIdUsuario());
        dto.setIdEquipo(suscripcion.getId().getIdEquipo());
        dto.setFechaSuscripcion(suscripcion.getFechaSuscripcion());

        try {
            // ⚙️ URLs de otros microservicios (ajustá según tu arquitectura)
            UsuarioDTO usuario = restTemplate.getForObject(
                    "http://usuarios-service/api/usuarios/" + suscripcion.getId().getIdUsuario(),
                    UsuarioDTO.class);

            EquipoDTO equipo = restTemplate.getForObject(
                    "http://equipos-service/api/equipos/" + suscripcion.getId().getIdEquipo(),
                    EquipoDTO.class);

            dto.setUsuario(usuario);
            dto.setEquipo(equipo);
        } catch (Exception e) {
            System.out.println("Error al obtener datos externos: " + e.getMessage());
        }

        return dto;
    }
}
