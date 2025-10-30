package dobleyfalta.partidos_services.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;

import dobleyfalta.partidos_services.models.Jornada;
import dobleyfalta.partidos_services.services.JornadaService;

@RestController
@RequestMapping("/api/jornada")
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

    @PostMapping("/add")
    public ResponseEntity<Jornada> addJornada(@RequestBody Jornada jornada) {
        Jornada nuevaJornada = jornadaService.crearJornada(jornada);
        return ResponseEntity.ok(nuevaJornada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Jornada> editarJornada(@PathVariable Integer id, @RequestBody Jornada jornada) {
        Jornada jornadaEditada = jornadaService.editarJornada(id, jornada);
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
