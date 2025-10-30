package dobleyfalta.equipo_service.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import dobleyfalta.equipo_service.models.Equipo;
import dobleyfalta.equipo_service.service.EquipoService;

@RestController
@RequestMapping("/api/equipos")
@CrossOrigin(origins = "*")
public class EquipoController {

    private final EquipoService equipoService;

    public EquipoController(EquipoService equipoService) {
        this.equipoService = equipoService;
    }

    @GetMapping
    public ResponseEntity<List<Equipo>> getAll() {
        return ResponseEntity.ok(equipoService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipo> getById(@PathVariable Integer id) {
        Equipo equipo = equipoService.getById(id);
        return equipo != null ? ResponseEntity.ok(equipo) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Equipo> crear(@RequestBody Equipo equipo) {
        return ResponseEntity.ok(equipoService.crearEquipo(equipo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Equipo> editar(@PathVariable Integer id, @RequestBody Equipo equipo) {
        Equipo editado = equipoService.editarEquipo(id, equipo);
        return editado != null ? ResponseEntity.ok(editado) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        Equipo eliminado = equipoService.eliminarEquipo(id);
        return eliminado != null ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
