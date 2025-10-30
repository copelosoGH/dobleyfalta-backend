package dobleyfalta.partidos_services.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipoDTO {
    
    private Integer idEquipo;
    private String nombre;
    private String logo;
}
