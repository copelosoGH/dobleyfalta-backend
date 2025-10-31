package dobleyfalta.equipo_service.models;

import java.sql.Date;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "suscripcionequipo")
public class SuscripcionEquipo {

    @EmbeddedId
    private SuscripcionEquipoId id;  // clave compuesta

    @Column(name = "fecha_suscripcion")
    private Date fechaSuscripcion;
}
