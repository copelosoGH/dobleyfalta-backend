package dobleyfalta.productos_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class InventarioId implements Serializable {

    @Column(name = "id_producto")
    private Integer idProducto;

    @Column(name = "color", length = 50)
    private String color;

    @Enumerated(EnumType.STRING)
    @Column(name = "talle", length = 2)
    private Talle talle;

    public InventarioId() {}

    public InventarioId(Integer producto, String color, Talle talle) {
        this.idProducto = producto;
        this.color = color;
        this.talle = talle;
    }

    // Getters y Setters
    public Integer getIdProducto() { return idProducto; }
    public void setIdProducto(Integer idProducto) { this.idProducto = idProducto; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public Talle getTalle() { return talle; }
    public void setTalle(Talle talle) { this.talle = talle; }

    // equals & hashCode (importantes para claves compuestas)

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InventarioId that)) return false;
        return Objects.equals(idProducto, that.idProducto) &&
               Objects.equals(color, that.color) &&
               talle == that.talle;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProducto, color, talle);
    }
}