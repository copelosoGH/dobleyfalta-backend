package dobleyfalta.productos_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import dobleyfalta.productos_service.model.Producto;
import dobleyfalta.productos_service.repository.ProductoRepository;

@Service
public class ProductoService {
    
    private final ProductoRepository productoRepository;
    
    public ProductoService(ProductoRepository productoRepository){
        this.productoRepository = productoRepository; 
    }

    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    public Producto getProductoById(Integer id) {
        return productoRepository.findById(id).orElse(null);
    }

    public Producto saveProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public void deleteProducto(Integer id) {
        productoRepository.deleteById(id);
    }

}
