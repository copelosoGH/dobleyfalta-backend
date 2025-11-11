package dobleyfalta.productos_service.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dobleyfalta.productos_service.model.Inventario;
import dobleyfalta.productos_service.model.InventarioId;
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

    @Transactional
    public Producto guardarProducto(Producto producto) {
        boolean esNuevo = producto.getIdProducto() == null;

        // Guardar primero el producto (para obtener ID si es nuevo)
        Producto productoGuardado = productoRepository.save(producto);

        // Sincronizar el inventario con el ID del producto
        if (producto.getInventario() != null) {
            if(esNuevo){
                productoGuardado.getInventario().clear();
            }
            
            for (Inventario inv : producto.getInventario()){

                // asegurar que el id compuesto tenga el id del producto
                if(inv.getId()==null){
                    inv.setId(new InventarioId());                    
                }

                // sincroniza el id del producto dentro del InventarioId
                inv.getId().setIdProducto(productoGuardado.getIdProducto());
                inv.setProducto(productoGuardado);

                if(esNuevo){
                    productoGuardado.getInventario().add(inv);
                }
            }

        }

        return productoRepository.save(productoGuardado);
    }

    @Transactional
    public void eliminarProducto(Integer id) {
        Producto producto = productoRepository.findById(id).orElse(null);
        if (producto != null) {
            producto.getInventario().clear(); // limpia la lista del inventario antes de eliminar el producto, esto evita errores
            productoRepository.delete(producto);
        }
    }

}
