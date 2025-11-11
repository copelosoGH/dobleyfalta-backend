package dobleyfalta.partidos_services.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TablaDTO {
    private Integer puesto;
    private String equipo;
    private Integer puntos;
    private Integer pj;
    private Integer pg;
    private Integer pp;
    private Integer pf;
    private Integer pc;
}
