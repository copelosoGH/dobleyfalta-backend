package dobleyfalta.partidos_services.DTO;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JornadaRequestDTO {
    private Integer numero;
    private Date fechaInicio;
    private Date fechaFin;
    private Integer idLiga; // Solo el ID de la liga
}

