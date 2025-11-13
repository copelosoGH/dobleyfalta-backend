package dobleyfalta.partidos_services.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import dobleyfalta.partidos_services.DTO.CrearPartidoRequestDTO;
import dobleyfalta.partidos_services.DTO.EquipoDTO;
import dobleyfalta.partidos_services.DTO.MarcadorUpdatedRequest;
import dobleyfalta.partidos_services.DTO.PartidoDTO;
import dobleyfalta.partidos_services.events.PartidoUpdatedEvent;
import dobleyfalta.partidos_services.models.EstadoPartido;
import dobleyfalta.partidos_services.models.Jornada;
import dobleyfalta.partidos_services.models.Partido;
import dobleyfalta.partidos_services.repository.PartidoRespository;
import jakarta.transaction.Transactional;

@Service
public class PartidoService {

    private final PartidoRespository partidoRespository;
    private final RestTemplate restTemplate;
    private final ApplicationEventPublisher eventPublisher;
    private final JornadaService jornadaService;

    private final String EQUIPO_SERVICE_URL = "http://localhost:8080/api/equipos/";

    public PartidoService(PartidoRespository partidoRespository, RestTemplate restTemplate, 
    ApplicationEventPublisher eventPublisher, JornadaService jornadaService) {
        this.partidoRespository = partidoRespository;
        this.restTemplate = restTemplate;
        this.eventPublisher = eventPublisher;
        this.jornadaService = jornadaService;
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
    
    // ... otros métodos

    // MÉTODOS DE CREACIÓN ACTUALIZADO (Recibe DTO, no la entidad)
    public Partido crearPartido(CrearPartidoRequestDTO partidoDto) {
        // 1. OBTENER LA JORNADA
        Jornada jornada = jornadaService.getJornadaById(partidoDto.getIdJornada());
        
        if (jornada == null) {
            // Lanza una excepción si la jornada no existe (buena práctica)
            throw new IllegalArgumentException("Jornada con ID " + partidoDto.getIdJornada() + " no encontrada.");
        }

        // 2. CONSTRUIR LA ENTIDAD PARTIDO
        Partido partido = new Partido();
        // Mapeo manual del DTO a la Entidad
        partido.setFecha(partidoDto.getFecha());
        partido.setPuntosLocal(partidoDto.getPuntosLocal() != null ? partidoDto.getPuntosLocal() : 0);
        partido.setPuntosVisitante(partidoDto.getPuntosVisitante() != null ? partidoDto.getPuntosVisitante() : 0);
        partido.setIdEquipoLocal(partidoDto.getIdEquipoLocal());
        partido.setIdEquipoVisitante(partidoDto.getIdEquipoVisitante());
        
        // Asignar el estado (y si es nulo, le ponemos 'proximo' por defecto)
        partido.setEstadoPartido(partidoDto.getEstadoPartido() != null ? partidoDto.getEstadoPartido() : EstadoPartido.proximo);
        
        // Asignar el objeto de relación OBLIGATORIO
        partido.setJornada(jornada); 

        // 3. Guardar y retornar
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
            partidoEdit.setEstadoPartido(partido.getEstadoPartido());
            return partidoRespository.save(partidoEdit);
        }
        return null;
    }

    @Transactional
    public boolean updatedMarcador(Integer partidoId, MarcadorUpdatedRequest request) {
        
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
