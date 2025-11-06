package dobleyfalta.usuarios_service.controller;

import org.springframework.web.bind.annotation.*;
import dobleyfalta.usuarios_service.service.UsuarioService;
import dobleyfalta.usuarios_service.dto.UsuarioUpdateDTO;
import dobleyfalta.usuarios_service.model.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public List<Usuario> getUsuarios() {
        return service.getAllUsuarios();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Integer id) {
        Usuario usuario = service.getUsuarioById(id);
        return usuario != null ? ResponseEntity.ok(usuario) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> createUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario usuario_guardado = service.createUsuario(usuario);
            usuario_guardado.setContrasena(null);
            return new ResponseEntity<>(usuario_guardado, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("mensaje", "El correo ya está registrado"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUsuario(@PathVariable Integer id, @RequestBody UsuarioUpdateDTO dto) {
        try {
            Usuario usuarioActualizado = service.updateUsuario(id, dto);
            usuarioActualizado.setContrasena(null);
            return ResponseEntity.ok(usuarioActualizado);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("mensaje", "El correo ya está registrado"));
        }
    }

    @GetMapping("/correo/{correo}")
    public ResponseEntity<Usuario> getByCorreo(@PathVariable String correo) {
        return service.getByCorreo(correo)
                .map(usuario -> ResponseEntity.ok(usuario))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUsuario(@PathVariable Integer id) {
        try {
            service.deleteUsuario(id);
            return ResponseEntity.ok(Map.of(
                    "mensaje", "Usuario eliminado correctamente",
                    "codigo", 200));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                            "mensaje", e.getMessage(),
                            "codigo", 404));
        }
    }
}
