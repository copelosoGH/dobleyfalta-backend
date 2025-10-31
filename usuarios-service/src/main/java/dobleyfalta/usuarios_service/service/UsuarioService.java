package dobleyfalta.usuarios_service.service;

import org.springframework.stereotype.Service;
import dobleyfalta.usuarios_service.repository.UsuarioRepository;
import dobleyfalta.usuarios_service.dto.UsuarioUpdateDTO;
import dobleyfalta.usuarios_service.model.Usuario;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.List;
import java.util.Optional;

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

    public Usuario getUsuarioById(Integer id){
        return repo.getByIdUsuario(id);
    }

    public Usuario createUsuario(Usuario usuario) {
        // Hashea la contraseña antes de guardar (si querés no hashearla, comenta esta
        // línea)
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        return repo.save(usuario);
    }

    public Usuario updateUsuario(Integer id, UsuarioUpdateDTO dto) {
        Usuario usuarioExistente = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (dto.getNombre() != null && !dto.getNombre().isEmpty()) {
            usuarioExistente.setNombre(dto.getNombre());
        }

        if (dto.getCorreo() != null && !dto.getCorreo().isEmpty()) {
            usuarioExistente.setCorreo(dto.getCorreo());
        }

        if (dto.getNuevaContrasena() != null && !dto.getNuevaContrasena().isEmpty()) {
            usuarioExistente.setContrasena(passwordEncoder.encode(dto.getNuevaContrasena()));
        }

        return repo.save(usuarioExistente);
    }

    public void deleteUsuario(Integer id) {
        Usuario usuarioExistente = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        repo.delete(usuarioExistente);
    }

    public Optional<Usuario> getByCorreo(String correo) {
        return repo.findByCorreo(correo);
    }
}
