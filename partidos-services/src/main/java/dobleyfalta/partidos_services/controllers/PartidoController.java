package dobleyfalta.partidos_services.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dobleyfalta.partidos_services.DTO.CrearPartidoRequestDTO;
import dobleyfalta.partidos_services.DTO.MarcadorUpdatedRequest;
import dobleyfalta.partidos_services.models.Partido;
import dobleyfalta.partidos_services.services.PartidoService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/partidos")
public class PartidoController {

    private final PartidoService partidoService;

    public PartidoController(PartidoService partidoService) {
        this.partidoService = partidoService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Partido>> getAllPartidos() {
        return ResponseEntity.ok(partidoService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Partido> getPartidoById(@PathVariable Integer id) {
        if (id == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(partidoService.getPartidoById(id));
    }

    @GetMapping("/jornadas/{jornadaId}")
    public ResponseEntity<List<Partido>> getPartidosByJornada(@PathVariable Integer jornadaId) {
        if (jornadaId == null) {
            return ResponseEntity.badRequest().build();
        }
        List<Partido> partidos = partidoService.getPartidosByJornada(jornadaId);
        return ResponseEntity.ok(partidos);
    }

    @PostMapping("/add")
    public ResponseEntity<Partido> addPartido(@RequestBody CrearPartidoRequestDTO partidoDto) {
        if (partidoDto == null) {
            return ResponseEntity.badRequest().build();
        }
        // Llama al servicio con el DTO
        try {
            Partido newPartido = partidoService.crearPartido(partidoDto);
            return ResponseEntity.ok(newPartido);
        } catch (IllegalArgumentException e) {
            // Maneja el caso de que la Jornada no exista, por ejemplo.
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Partido> editarPartido(@PathVariable Integer id, @RequestBody Partido partido) {
        if (id == null || partido == null) {
            return ResponseEntity.notFound().build();
        }
        Partido actualizarCarrito = partidoService.editarPartido(id, partido);
        return ResponseEntity.ok(actualizarCarrito);
    }

    @PutMapping("puntajes/{id}")
    public ResponseEntity<Void> updateScore(
            @PathVariable("id") Integer id,
            @Validated @RequestBody MarcadorUpdatedRequest request) {

        // 1. Validación de ID (Aunque @PathVariable lo valida en parte)
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }

        // 2. Llamada a la lógica de negocio
        boolean success = partidoService.updatedMarcador(id, request);

        if (!success) {
            // Retorna 404 NOT FOUND si el ID del partido no existe
            // o 400 BAD REQUEST si el 'equipo' en el cuerpo es inválido.
            // Dado que el servicio retorna 'false' en ambos casos, asumimos 404 si no hubo
            // actualización.
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // 3. Respuesta HTTP: 204 No Content
        return ResponseEntity.noContent().build();
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
