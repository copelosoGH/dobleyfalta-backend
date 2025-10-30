package dobleyfalta.equipo_service.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipoDTO {
    
    private Integer id;
    private String nombre;
    private String categoria;
    private String ciudad;

}
