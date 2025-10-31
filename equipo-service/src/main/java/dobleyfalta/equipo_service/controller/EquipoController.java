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

    @GetMapping("/all")
    public ResponseEntity<List<Equipo>> getEquiposAll() {
        return ResponseEntity.ok(equipoService.getEquiposAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipo> getEquipoById(@PathVariable Integer id) {
        Equipo equipo = equipoService.getEquipoById(id);
        return equipo != null ? ResponseEntity.ok(equipo) : ResponseEntity.notFound().build();
    }

    @PostMapping("/add")
    public ResponseEntity<Equipo> crearEquipo(@RequestBody Equipo equipo) {
        return ResponseEntity.ok(equipoService.crearEquipo(equipo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Equipo> editarEquipo(@PathVariable Integer id, @RequestBody Equipo equipo) {
        Equipo editado = equipoService.editarEquipo(id, equipo);
        return editado != null ? ResponseEntity.ok(editado) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEquipo(@PathVariable Integer id) {
        Equipo eliminado = equipoService.eliminarEquipo(id);
        return eliminado != null ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
