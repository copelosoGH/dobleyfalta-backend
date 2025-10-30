package dobleyfalta.productos_service.repository;

import dobleyfalta.productos_service.model.Inventario;
import dobleyfalta.productos_service.model.InventarioId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, InventarioId> {
    List<Inventario> findByProducto_IdProducto(Integer idProducto);
}