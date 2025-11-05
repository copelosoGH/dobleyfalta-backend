package dobleyfalta.usuarios_service.dto;

import dobleyfalta.usuarios_service.model.Rol;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioUpdateDTO {
    private String nombre;
    private String correo;
    private String nuevaContrasena;
    private Rol rol;
}
