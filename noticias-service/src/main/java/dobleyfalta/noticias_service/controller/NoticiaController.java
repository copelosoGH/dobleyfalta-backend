package dobleyfalta.noticias_service.controller;

import dobleyfalta.noticias_service.model.Noticia;
import dobleyfalta.noticias_service.service.NoticiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/noticias")
public class NoticiaController {

    @Autowired
    private NoticiaService noticiaService;

    @GetMapping
    public ResponseEntity<List<Noticia>> obtenerTodas() {
        List<Noticia> noticias = noticiaService.obtenerTodas();
        return ResponseEntity.ok(noticias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer id) {
        Optional<Noticia> noticia = noticiaService.obtenerPorId(id);
        if (noticia.isPresent()) {
            return ResponseEntity.ok(noticia.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Noticia no encontrada");
        }
    }

    @PostMapping
    public ResponseEntity<Noticia> crear(@RequestBody Noticia noticia) {
        Noticia nueva = noticiaService.guardar(noticia);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody Noticia noticia) {
        Noticia actualizada = noticiaService.actualizar(id, noticia);
        if (actualizada != null) {
            return ResponseEntity.ok(actualizada);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Noticia no encontrada para actualizar");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        Optional<Noticia> noticia = noticiaService.obtenerPorId(id);
        if (noticia.isPresent()) {
            noticiaService.eliminar(id);
            return ResponseEntity.ok("Noticia eliminada correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la noticia a eliminar");
        }
    }
}
