package dobleyfalta.usuarios_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import dobleyfalta.usuarios_service.model.Usuario;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByCorreo(String correo);
}