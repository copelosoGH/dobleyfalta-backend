package dobleyfalta.partidos_services.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarcadorUpdatedRequest {

    private String equipo; // "LOCAL" o "VISITANTE"
    private Integer puntos; // El nuevo puntaje final.
}
