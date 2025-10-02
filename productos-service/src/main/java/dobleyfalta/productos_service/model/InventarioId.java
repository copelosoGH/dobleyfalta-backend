/* package dobleyfalta.productos_service.model;

import java.io.Serializable;
import java.util.Objects;

public class InventarioId implements Serializable {

    private Integer producto;
    private String color;
    private Talle talle;

    public InventarioId() {}

    public InventarioId(Integer producto, String color, Talle talle) {
        this.producto = producto;
        this.color = color;
        this.talle = talle;
    }

    // equals & hashCode (importantes para claves compuestas)

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InventarioId that)) return false;
        return Objects.equals(producto, that.producto) &&
               Objects.equals(color, that.color) &&
               talle == that.talle;
    }

    @Override
    public int hashCode() {
        return Objects.hash(producto, color, talle);
    }
}
 */