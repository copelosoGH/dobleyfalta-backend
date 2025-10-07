package dobleyfalta.compras_services.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleCarritoDTO {
    
    private ProductoDTO producto;
    private Integer cantidad;
}
