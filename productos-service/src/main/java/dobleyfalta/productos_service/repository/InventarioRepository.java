/* package dobleyfalta.productos_service.repository;

import dobleyfalta.productos_service.model.Inventario;
import dobleyfalta.productos_service.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Producto> {
 // Acabo de cambiar la línea de JpaRepoisitory. Antes JpaRepository<Inventario, InventarioId>
 // Después es necesario saber el porqué se creó el inventarioId y si es necesario que siga existiendo. 
 // También si lo tengo que borrar tengo que saber el cómo hacer para que todo siga funcionando correctamente
 // Revisar nuevamente toda la conexión e implementación del inventario con el producto
 // mi objetivo es que cuando pida detalles del producto, también otorgue los del inventario que está ligado a cada producto
 // Ver cómo hacer toda la implementacón.
    List<Inventario> findByProducto_IdProducto(Integer idProducto); // útil para ver inventario de un producto
}
 */