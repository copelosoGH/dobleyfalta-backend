package dobleyfalta.compras_services.DTO;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class CompraDTO {
    
    private Integer idCompra;
    private LocalDateTime fechaCompra;
    private Float montoTotal;
    private String metodoPago;
    private Integer idCarrito;
    private Integer idUsuario;
    private LocalDateTime fechaCreacion;
    private List<DetalleCarritoDTO> detalles;
}
