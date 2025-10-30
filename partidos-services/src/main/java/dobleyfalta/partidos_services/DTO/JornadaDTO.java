package dobleyfalta.partidos_services.DTO;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JornadaDTO {
    private Integer idJornada;
    private Integer numero;
    private Date fechaInicio;
    private Date fechaFin;
    private LigaDTO liga;
}
