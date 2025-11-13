package dobleyfalta.partidos_services.services;

import java.util.List;

import org.springframework.stereotype.Service;

import dobleyfalta.partidos_services.DTO.JornadaRequestDTO;
import dobleyfalta.partidos_services.models.Jornada;
import dobleyfalta.partidos_services.models.Liga;
import dobleyfalta.partidos_services.repository.JornadaRepository;
import dobleyfalta.partidos_services.repository.LigaRepository;

@Service
public class JornadaService {

    private final JornadaRepository jornadaRepository;

    private final LigaRepository ligaRepository;

    public JornadaService(JornadaRepository jornadaRepository, LigaRepository ligaRepository) {
        this.jornadaRepository = jornadaRepository;
        this.ligaRepository = ligaRepository;
    }

    public List<Jornada> getAll() {
        return jornadaRepository.findAll();
    }

    public Jornada getJornadaById(Integer id) {
        return jornadaRepository.findByIdJornada(id);
    }

    public List<Jornada> getJornadasByLiga(Integer idLiga) {
        return jornadaRepository.findByLigaIdLiga(idLiga);
    }

    public Jornada crearJornada(JornadaRequestDTO dto) {
        Liga liga = ligaRepository.findById(dto.getIdLiga())
                .orElseThrow(() -> new RuntimeException("Liga no encontrada"));

        Jornada jornada = new Jornada();
        jornada.setNumero(dto.getNumero());
        jornada.setFechaInicio(dto.getFechaInicio());
        jornada.setFechaFin(dto.getFechaFin());
        jornada.setLiga(liga);

        return jornadaRepository.save(jornada);
    }

    // Editar jornada usando DTO
    public Jornada editarJornada(Integer id, JornadaRequestDTO dto) {
        Jornada jornadaEdit = jornadaRepository.findByIdJornada(id);
        if (jornadaEdit == null) {
            return null;
        }

        Liga liga = ligaRepository.findById(dto.getIdLiga())
                .orElseThrow(() -> new RuntimeException("Liga no encontrada"));

        jornadaEdit.setNumero(dto.getNumero());
        jornadaEdit.setFechaInicio(dto.getFechaInicio());
        jornadaEdit.setFechaFin(dto.getFechaFin());
        jornadaEdit.setLiga(liga);

        return jornadaRepository.save(jornadaEdit);
    }

    public Jornada eliminarJornada(Integer id) {
        Jornada jornada = jornadaRepository.findByIdJornada(id);
        if (jornada != null) {
            jornadaRepository.delete(jornada);
            return jornada;
        }
        return null;
    }
}
