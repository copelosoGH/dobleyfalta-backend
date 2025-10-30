package dobleyfalta.equipo_service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import dobleyfalta.equipo_service.DTO.SuscripcionEquipoDTO;
import dobleyfalta.equipo_service.models.SuscripcionEquipo;
import dobleyfalta.equipo_service.service.SuscripcionEquipoService;

@RestController
@RequestMapping("/api/suscripciones")
public class SuscripcionEquipoController {

    private final SuscripcionEquipoService suscripcionService;

    public SuscripcionEquipoController(SuscripcionEquipoService suscripcionService) {
        this.suscripcionService = suscripcionService;
    }

    @GetMapping
    public ResponseEntity<List<SuscripcionEquipoDTO>> getAll() {
        return ResponseEntity.ok(suscripcionService.getAll());
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<SuscripcionEquipoDTO>> getByUsuario(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(suscripcionService.getByUsuario(idUsuario));
    }

    @GetMapping("/equipo/{idEquipo}")
    public ResponseEntity<List<SuscripcionEquipoDTO>> getByEquipo(@PathVariable Integer idEquipo) {
        return ResponseEntity.ok(suscripcionService.getByEquipo(idEquipo));
    }

    @PostMapping
    public ResponseEntity<SuscripcionEquipo> suscribirse(
            @RequestParam Integer idUsuario,
            @RequestParam Integer idEquipo) {
        SuscripcionEquipo nueva = suscripcionService.suscribirse(idUsuario, idEquipo);
        return nueva != null ? ResponseEntity.ok(nueva) : ResponseEntity.badRequest().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> eliminar(
            @RequestParam Integer idUsuario,
            @RequestParam Integer idEquipo) {
        suscripcionService.eliminarSuscripcion(idUsuario, idEquipo);
        return ResponseEntity.ok().build();
    }
}
