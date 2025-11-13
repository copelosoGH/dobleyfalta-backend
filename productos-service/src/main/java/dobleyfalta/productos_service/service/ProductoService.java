package dobleyfalta.productos_service.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import dobleyfalta.productos_service.model.Inventario;
import dobleyfalta.productos_service.model.InventarioId;
import dobleyfalta.productos_service.model.Producto;
import dobleyfalta.productos_service.repository.ProductoRepository;


@Service
public class ProductoService {
    
    @Autowired
    private final ProductoRepository productoRepository;

    @Autowired
    private ImagenService imagenService;
    
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
    public Producto crearProducto(Producto producto) {
        try{
            if (producto.getImagen() != null && !producto.getImagen().isEmpty()){
                String nombreArchivo = "producto_" + System.currentTimeMillis() + ".jpg";
                String rutaGuardada = imagenService.guardarImagenBase64(producto.getImagen(), nombreArchivo);
                producto.setImagen(rutaGuardada);
            }
        }catch(IOException e){
            throw new RuntimeException("Error al guardar la imagen: " + e.getMessage());
        }

        // Guardamos el producto primero (sin inventario) para obtener el ID
        Producto productoGuardado = productoRepository.save(producto);

        // Si viene inventario en el body, configuramos las relaciones
        if (producto.getInventario() != null) {
            for (Inventario inv : producto.getInventario()) {
                if (inv.getId() == null) inv.setId(new InventarioId());
                inv.getId().setIdProducto(productoGuardado.getIdProducto());
                inv.setProducto(productoGuardado);
            }

            productoGuardado.setInventario(producto.getInventario());
        }

        return productoRepository.save(productoGuardado);
    }

    @Transactional
    public Producto actualizarProducto(Integer id, Producto producto) {
        Producto existente = productoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // Actualiza solo campos presentes
        if (producto.getNombre() != null) existente.setNombre(producto.getNombre());
        if (producto.getDescripcion() != null) existente.setDescripcion(producto.getDescripcion());
        if (producto.getPrecio() != null) existente.setPrecio(producto.getPrecio());

        try{
            String imagenNueva = producto.getImagen();
            if (imagenNueva != null && !imagenNueva.isEmpty()) {
                String imagenVieja = existente.getImagen();
                if (imagenVieja != null && !imagenVieja.isEmpty()) {
                    File archivoViejo = new File(imagenVieja);
                    if (archivoViejo.exists()) {
                        boolean eliminada = archivoViejo.delete();
                        if (!eliminada) {
                            System.err.println("No se pudo eliminar la imagen vieja: " + imagenVieja);
                        }
                    }
                }
                String nombreArchivo = "noticia_" + System.currentTimeMillis() + ".jpg";
                String rutaGuardada = imagenService.guardarImagenBase64(imagenNueva, nombreArchivo);
                existente.setImagen(rutaGuardada);
            }
        } catch (IOException e){
            throw new RuntimeException("Error al actualizar la imagen: " + e.getMessage());
        }
    return productoRepository.save(existente);
    }

    // Actualizar inventario
    private void actualizarInventario(Producto existente, Producto producto) {
        if (producto.getInventario() != null) {
            for (Inventario invNuevo : producto.getInventario()) {
                var invExistente = existente.getInventario().stream()
                        .filter(i -> i.getId().equals(invNuevo.getId()))
                        .findFirst()
                        .orElse(null);
                if (invExistente != null) {
                    // Si ya existe, actualiza solo el stock
                    invExistente.setStock(invNuevo.getStock());
                } else {
                    // Si no existe, lo agrega al inventario
                    invNuevo.setProducto(existente);
                    if (invNuevo.getId() == null) invNuevo.setId(new InventarioId());
                    invNuevo.getId().setIdProducto(existente.getIdProducto());
                    existente.getInventario().add(invNuevo);
                }
            }
        }
    // Asegurar bidireccionalidad antes de guardar
    existente.getInventario().forEach(inv -> inv.setProducto(existente));
    }

    @Transactional
    public void eliminarProducto(Integer id) {
        Producto producto = productoRepository.findById(id).orElse(null);
        if (producto != null) {
            producto.getInventario().clear(); // limpia la lista del inventario antes de eliminar el producto, esto evita errores
            
            String rutaImagen = producto.getImagen();
            if (rutaImagen != null && !rutaImagen.isEmpty()) {
                File archivo = new File(rutaImagen);
                if (archivo.exists()) {
                    boolean eliminada = archivo.delete();
                    if (!eliminada) {
                        System.err.println("No se pudo eliminar la imagen: " + rutaImagen);
                    }
                }
            }

            productoRepository.delete(producto);
        }

    }

}
