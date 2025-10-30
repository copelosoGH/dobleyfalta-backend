package dobleyfalta.equipo_service.models;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class SuscripcionEquipoId implements Serializable {

    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(name = "id_equipo")
    private Integer idEquipo;
}
