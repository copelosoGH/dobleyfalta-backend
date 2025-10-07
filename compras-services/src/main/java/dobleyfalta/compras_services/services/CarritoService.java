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

    public List<Carrito> getAll() {
        return carritoRepository.findAll();
    }

    public Carrito getCarritoById(Integer id) {
        return carritoRepository.findByIdCarrito(id);
    }

    public Carrito crearCarrito(Carrito carrito) {
        return carritoRepository.save(carrito);
    }

    public Carrito editarCarrito(Integer id, Carrito carrito) {
        Carrito carritoEdit = carritoRepository.findByIdCarrito(id);
        if (carritoEdit != null) {
            carritoEdit.setIdUsuario(carrito.getIdUsuario());
            carritoEdit.setFechaCreacion(carrito.getFechaCreacion());
            return carritoRepository.save(carritoEdit);
        }
        return null;
    }

    public boolean eliminarCarrito(Integer id) {
        Carrito carrito = carritoRepository.findByIdCarrito(id);
        if (carrito != null) {
            carritoRepository.delete(carrito);
            return true;
        }
        return false;
    }
}
