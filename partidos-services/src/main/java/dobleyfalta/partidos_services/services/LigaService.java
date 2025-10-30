package dobleyfalta.partidos_services.services;

import java.util.List;

import org.springframework.stereotype.Service;

import dobleyfalta.partidos_services.models.Liga;
import dobleyfalta.partidos_services.repository.LigaRepository;

@Service
public class LigaService {

    private final LigaRepository ligaRepository;

    public LigaService(LigaRepository ligaRepository) {
        this.ligaRepository = ligaRepository;
    }

    public List<Liga> getAll() {
        return ligaRepository.findAll();
    }

    public Liga getLigaById(Integer id) {
        return ligaRepository.findByIdLiga(id);
    }

    public Liga crearLiga(Liga liga) {
        return ligaRepository.save(liga);
    }

    public Liga editarLiga(Integer id, Liga liga) {
        Liga ligaEdit = ligaRepository.findByIdLiga(id);
        if (ligaEdit != null) {
            ligaEdit.setNombre(liga.getNombre());
            ligaEdit.setFechaInicio(liga.getFechaInicio());
            ligaEdit.setFechaFin(liga.getFechaFin());
            ligaEdit.setAnio(liga.getAnio());
            return ligaRepository.save(ligaEdit);
        }
        return null;
    }

    public Liga eliminarLiga(Integer id) {
        Liga liga = ligaRepository.findByIdLiga(id);
        if (liga != null) {
            ligaRepository.delete(liga);
            return liga;
        }
        return null;
    }
}
