package dobleyfalta.compras_services.models.PK;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class DetalleCarritoId implements Serializable{
    
    private Integer idProducto;
    private Integer idCarrito;

}
