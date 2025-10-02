package dobleyfalta.compras_services.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dobleyfalta.compras_services.models.Carrito;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Integer> {

    Carrito findByIdCarrito(Integer idCarrito);

    List<Carrito> findAll();

    
}
