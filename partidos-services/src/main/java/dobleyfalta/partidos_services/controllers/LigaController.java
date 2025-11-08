package dobleyfalta.partidos_services.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;

import dobleyfalta.partidos_services.models.Liga;
import dobleyfalta.partidos_services.services.LigaService;

@RestController
@RequestMapping("/api/ligas")
public class LigaController {

    private final LigaService ligaService;

    public LigaController(LigaService ligaService) {
        this.ligaService = ligaService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Liga>> getAllLigas() {
        return ResponseEntity.ok(ligaService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Liga> getLigaById(@PathVariable Integer id) {
        Liga liga = ligaService.getLigaById(id);
        if (liga == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(liga);
    }

    @PostMapping("/add")
    public ResponseEntity<Liga> addLiga(@RequestBody Liga liga) {
        Liga nuevaLiga = ligaService.crearLiga(liga);
        return ResponseEntity.ok(nuevaLiga);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Liga> editarLiga(@PathVariable Integer id, @RequestBody Liga liga) {
        Liga ligaEditada = ligaService.editarLiga(id, liga);
        if (ligaEditada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ligaEditada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarLiga(@PathVariable Integer id) {
        Liga eliminada = ligaService.eliminarLiga(id);
        if (eliminada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
