package dobleyfalta.compras_services.models;

import dobleyfalta.compras_services.models.PK.DetalleCarritoId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "detallecarrito")
public class DetalleCarrito {
    
    @EmbeddedId
    private DetalleCarritoId idDetalleCarrito;

    @Column(name = "cantidad")
    private Integer cantidad;

}
