package dobleyfalta.partidos_services.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import dobleyfalta.partidos_services.DTO.EquipoDTO;
import dobleyfalta.partidos_services.DTO.PartidoDTO;
import dobleyfalta.partidos_services.models.Partido;
import dobleyfalta.partidos_services.repository.PartidoRespository;

@Service
public class PartidoService {

    private final PartidoRespository partidoRespository;
    private final RestTemplate restTemplate;

    private final String EQUIPO_SERVICE_URL = "http://localhost:8080/api/equipos/";

    public PartidoService(PartidoRespository partidoRespository, RestTemplate restTemplate) {
        this.partidoRespository = partidoRespository;
        this.restTemplate = restTemplate;
    }

    public List<Partido> getAll() {
        return partidoRespository.findAll();
    }

    public Partido getPartidoById(Integer id){
        return partidoRespository.findByIdPartido(id);
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
