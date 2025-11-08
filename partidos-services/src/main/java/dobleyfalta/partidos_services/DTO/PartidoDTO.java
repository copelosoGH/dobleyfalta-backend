package dobleyfalta.partidos_services.DTO;

import java.sql.Date;

import dobleyfalta.partidos_services.models.EstadoPartido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartidoDTO {
    private Integer idPartido;
    private Date fecha;
    private Integer puntosLocal;
    private Integer puntosVisitante;
    private EstadoPartido estado;
    private EquipoDTO equipoLocal;
    private EquipoDTO equipoVisitante;
}
