package dobleyfalta.auth_service.dto;

import dobleyfalta.auth_service.model.Usuario;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private String token;
    private Usuario usuario;

    public LoginResponse(String token, Usuario usuario) {
        this.token = token;
        this.usuario = usuario;

        if (this.usuario != null) {
            this.usuario.setContrasena(null);
        }
    }
}