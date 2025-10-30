package dobleyfalta.partidos_services.services;

import java.util.List;

import org.springframework.stereotype.Service;

import dobleyfalta.partidos_services.models.Jornada;
import dobleyfalta.partidos_services.repository.JornadaRepository;

@Service
public class JornadaService {

    private final JornadaRepository jornadaRepository;

    public JornadaService(JornadaRepository jornadaRepository) {
        this.jornadaRepository = jornadaRepository;
    }

    public List<Jornada> getAll() {
        return jornadaRepository.findAll();
    }

    public Jornada getJornadaById(Integer id) {
        return jornadaRepository.findByIdJornada(id);
    }

    public Jornada crearJornada(Jornada jornada) {
        return jornadaRepository.save(jornada);
    }

    public Jornada editarJornada(Integer id, Jornada jornada) {
        Jornada jornadaEdit = jornadaRepository.findByIdJornada(id);
        if (jornadaEdit != null) {
            jornadaEdit.setNumero(jornada.getNumero());
            jornadaEdit.setFechaInicio(jornada.getFechaInicio());
            jornadaEdit.setFechaFin(jornada.getFechaFin());
            jornadaEdit.setLiga(jornada.getLiga());
            return jornadaRepository.save(jornadaEdit);
        }
        return null;
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
