package dobleyfalta.usuarios_service.controller;

import org.springframework.web.bind.annotation.*;
import dobleyfalta.usuarios_service.service.UsuarioService;
import dobleyfalta.usuarios_service.model.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @GetMapping
    public List<Usuario> getUsuarios() {
        return service.getAllUsuarios();
    }

    @PostMapping
    public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario usuario) {
        Usuario usuario_guardado = service.createUsuario(usuario);
        return new ResponseEntity<>(usuario_guardado, HttpStatus.CREATED);
    }

    @GetMapping("/correo/{correo}")
    public ResponseEntity<Usuario> getByCorreo(@PathVariable String correo) {
        return service.getByCorreo(correo)
                .map(usuario -> ResponseEntity.ok(usuario))
                .orElse(ResponseEntity.notFound().build());
    }
}
