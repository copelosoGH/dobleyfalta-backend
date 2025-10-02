/* ackage dobleyfalta.productos_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "inventario")

public class Inventario {

    @Id
    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    @Column(name = "color", length = 30)
    public String color;

    @Column(name = "talle", length = 1, nullable = false)
    public String talle;

    @Column(name = "stock", nullable = false)
    public Integer stock;

}
 */