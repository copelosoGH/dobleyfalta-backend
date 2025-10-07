package dobleyfalta.compras_services.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dobleyfalta.compras_services.models.Compra;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Integer> {
    
    Compra findByIdCompra(Integer idCompra);
}
