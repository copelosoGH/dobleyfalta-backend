package dobleyfalta.partidos_services.DTO;

import java.sql.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LigaDTO {
    private Integer idLiga;
    private String nombre;
    private Date fechaInicio;
    private Date fechaFin;
    private Integer anio;
    private List<JornadaDTO> jornadas;
}
