package dobleyfalta.usuarios_service.service;

import org.springframework.stereotype.Service;
import dobleyfalta.usuarios_service.repository.UsuarioRepository;
import dobleyfalta.usuarios_service.model.Usuario;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository repo;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Usuario> getAllUsuarios() {
        return repo.findAll();
    }

    public Usuario createUsuario(Usuario usuario) {
        // Hashea la contraseña antes de guardar (si querés no hashearla, comenta esta línea)
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        return repo.save(usuario);
    }
}
