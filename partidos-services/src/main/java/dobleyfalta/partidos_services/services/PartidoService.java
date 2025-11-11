package dobleyfalta.partidos_services.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import dobleyfalta.partidos_services.DTO.EquipoDTO;
import dobleyfalta.partidos_services.DTO.MarcadorUpdatedRequest;
import dobleyfalta.partidos_services.DTO.PartidoDTO;
import dobleyfalta.partidos_services.events.PartidoUpdatedEvent;
import dobleyfalta.partidos_services.models.Partido;
import dobleyfalta.partidos_services.repository.PartidoRespository;
import jakarta.transaction.Transactional;

@Service
public class PartidoService {

    private final PartidoRespository partidoRespository;
    private final RestTemplate restTemplate;
    private final ApplicationEventPublisher eventPublisher;

    private final String EQUIPO_SERVICE_URL = "http://localhost:8080/api/equipos/";

    public PartidoService(PartidoRespository partidoRespository, RestTemplate restTemplate, ApplicationEventPublisher eventPublisher) {
        this.partidoRespository = partidoRespository;
        this.restTemplate = restTemplate;
        this.eventPublisher = eventPublisher;
    }

    public List<Partido> getAll() {
        return partidoRespository.findAll();
    }

    public Partido getPartidoById(Integer id){
        return partidoRespository.findByIdPartido(id);
    }

    public List<Partido> getPartidosByJornada(Integer jornadaId) {
        return partidoRespository.findByJornadaIdJornada(jornadaId);
    }

    public PartidoDTO getPartidoConEquipos(Integer id) {
        Partido partido = partidoRespository.findByIdPartido(id);
        if (partido == null) {
            return null;
        }

        EquipoDTO equipoLocal = restTemplate.getForObject(EQUIPO_SERVICE_URL + partido.getIdEquipoLocal(), EquipoDTO.class);
        EquipoDTO equipoVisitante = restTemplate.getForObject(EQUIPO_SERVICE_URL + partido.getIdEquipoVisitante(), EquipoDTO.class);

        PartidoDTO dto = new PartidoDTO();
        dto.setIdPartido(partido.getIdPartido());
        dto.setEquipoLocal(equipoLocal);
        dto.setEquipoVisitante(equipoVisitante);
        dto.setFecha(partido.getFecha());
        dto.setPuntosLocal(partido.getPuntosLocal());
        dto.setPuntosVisitante(partido.getPuntosVisitante());
        dto.setEstado(partido.getEstadoPartido());
        return dto;
    }

    public Partido crearPartido(Partido partido) {
        return partidoRespository.save(partido);
    }

    public Partido editarPartido(Integer id, Partido partido) {
        Partido partidoEdit = partidoRespository.findByIdPartido(id);
        if (partidoEdit != null) {
            partidoEdit.setFecha(partido.getFecha());
            partidoEdit.setIdEquipoLocal(partido.getIdEquipoLocal());
            partidoEdit.setIdEquipoVisitante(partido.getIdEquipoVisitante());
            partidoEdit.setPuntosLocal(partido.getPuntosLocal());
            partidoEdit.setPuntosVisitante(partido.getPuntosVisitante());
            return partidoRespository.save(partidoEdit);
        }
        return null;
    }

    @Transactional
    public boolean updatedMarcador(Integer partidoId, MarcadorUpdatedRequest request) {
        
        // 1. Validar y Actualizar DB
        int updatedRows = 0;
        String equipo = request.getEquipo().toUpperCase();
        Integer puntos = request.getPuntos();

        if ("LOCAL".equals(equipo)) {
            updatedRows = partidoRespository.updatePuntosLocal(partidoId, puntos);
        } else if ("VISITANTE".equals(equipo)) {
            updatedRows = partidoRespository.updatePuntosVisitante(partidoId, puntos);
        } else {
            // Equipo inválido ("LOCAL"/"VISITANTE")
            return false; 
        }

        if (updatedRows == 0) {
             // El partido no existe (ID inválido)
             return false;
        }

        // 2. Propagación en Tiempo Real (Emisión de Evento)
        // El listener (WebSocket component) manejará el envío del mensaje actualizado.
        eventPublisher.publishEvent(new PartidoUpdatedEvent(partidoId));

        return true;
    }

    public Partido eliminarPartido(Integer id) {
        Partido partido = partidoRespository.findByIdPartido(id);
        if (partido != null) {
            partidoRespository.delete(partido);
            return partido;
        }
        return null;
    }

    public List<PartidoDTO> getAllPartidosConEquipos() {
        return partidoRespository.findAll().stream()
            .map(p -> getPartidoConEquipos(p.getIdPartido()))
            .collect(Collectors.toList());
    }
}
