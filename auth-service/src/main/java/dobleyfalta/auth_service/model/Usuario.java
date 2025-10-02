package dobleyfalta.auth_service.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Usuario {
    private Integer idUsuario;
    private String correo;
    private String contrasena;
    private String rol;
}
