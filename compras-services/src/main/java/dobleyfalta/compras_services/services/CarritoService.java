package dobleyfalta.compras_services.services;

import java.util.List;

import org.springframework.stereotype.Service;

import dobleyfalta.compras_services.models.Carrito;
import dobleyfalta.compras_services.repository.CarritoRepository;

@Service
public class CarritoService {
    
    private final CarritoRepository carritoRepository;

    public CarritoService(CarritoRepository carritoRepository) {
        this.carritoRepository = carritoRepository;
    }

    public List<Carrito> getCarritoRepository() {
        return carritoRepository.findAll().stream().toList();
    }
}
