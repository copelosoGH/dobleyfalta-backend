package dobleyfalta.usuarios_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioUpdateDTO {
    private String nombre;
    private String correo;
    private String nuevaContrasena;
}
