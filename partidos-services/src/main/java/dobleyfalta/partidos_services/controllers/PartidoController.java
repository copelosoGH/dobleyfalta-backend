package dobleyfalta.partidos_services.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dobleyfalta.partidos_services.models.Partido;
import dobleyfalta.partidos_services.services.PartidoService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/partido")
public class PartidoController {
    
    private final PartidoService partidoService;

    public PartidoController(PartidoService partidoService) {
        this.partidoService = partidoService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Partido>>getAllPartidos() {
        return ResponseEntity.ok(partidoService.getAll());
    }
    
    @GetMapping("/id")
    public ResponseEntity<Partido> getPartidoById(@PathVariable Integer id) {
        if (id == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(partidoService.getPartidoById(id));
    }
    
    @PostMapping("/add")
    public ResponseEntity<Partido> addCarrito(@RequestBody Partido partido) {
        if (partido == null) {
            return ResponseEntity.badRequest().build();
        }
        Partido newPartido = partidoService.crearPartido(partido);
        return ResponseEntity.ok(newPartido);
    }
 
    @PutMapping("/{id}")
    public ResponseEntity<Partido> editarCarrito(@PathVariable Integer id, @RequestBody Partido partido) {
        if (id == null || partido == null) {
            return ResponseEntity.notFound().build();
        }
        Partido actualizarCarrito = partidoService.editarPartido(id, partido);
        return ResponseEntity.ok(actualizarCarrito);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Partido> eliminarCarrito(@PathVariable Integer id) {
        Partido partidoEliminado = partidoService.eliminarPartido(id);
        if (partidoEliminado != null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
}
