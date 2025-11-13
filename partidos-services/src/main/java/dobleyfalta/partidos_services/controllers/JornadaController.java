package dobleyfalta.partidos_services.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;

import dobleyfalta.partidos_services.DTO.JornadaRequestDTO;
import dobleyfalta.partidos_services.models.Jornada;
import dobleyfalta.partidos_services.services.JornadaService;

@RestController
@RequestMapping("/api/jornadas")
public class JornadaController {

    private final JornadaService jornadaService;

    public JornadaController(JornadaService jornadaService) {
        this.jornadaService = jornadaService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Jornada>> getAllJornadas() {
        return ResponseEntity.ok(jornadaService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Jornada> getJornadaById(@PathVariable Integer id) {
        Jornada jornada = jornadaService.getJornadaById(id);
        if (jornada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(jornada);
    }

    @GetMapping("/ligas/{ligaId}")
    public ResponseEntity<List<Jornada>> getJornadasByLiga(@PathVariable Integer ligaId) {
        if (ligaId == null) {
            return ResponseEntity.badRequest().build(); 
        }
        List<Jornada> jornadas = jornadaService.getJornadasByLiga(ligaId);

        return ResponseEntity.ok(jornadas); 
    }

    @PostMapping("/add")
    public ResponseEntity<Jornada> addJornada(@RequestBody JornadaRequestDTO dto) {
        Jornada nuevaJornada = jornadaService.crearJornada(dto);
        return ResponseEntity.ok(nuevaJornada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Jornada> editarJornada(@PathVariable Integer id, @RequestBody JornadaRequestDTO dto) {
        Jornada jornadaEditada = jornadaService.editarJornada(id, dto);
        if (jornadaEditada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(jornadaEditada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarJornada(@PathVariable Integer id) {
        Jornada eliminada = jornadaService.eliminarJornada(id);
        if (eliminada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
