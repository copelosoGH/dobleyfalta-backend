package dobleyfalta.compras_services.DTO;

import lombok.Data;

@Data
public class ProductoDTO {
    
    private Integer idProducto;
    private String nombre;
    private String descripcion;
    private Double precio;
}
