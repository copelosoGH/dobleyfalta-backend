package dobleyfalta.productos_service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dobleyfalta.productos_service.model.Producto;
import dobleyfalta.productos_service.service.ProductoService;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Producto>> listarProductos() {
        return ResponseEntity.ok(productoService.getAllProductos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProducto(@PathVariable Integer id) {
        Producto producto = productoService.getProductoById(id);
        if (producto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(producto);
    }

    @PostMapping
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto) {
        return ResponseEntity.ok(productoService.guardarProducto(producto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Integer id, @RequestBody Producto producto) {
        Producto existe = productoService.getProductoById(id);
        if (existe == null) {
            return ResponseEntity.notFound().build();
        }

        // actualiza los campos solo si vienen del body
        if (producto.getNombre() != null) existe.setNombre(producto.getNombre());
        if (producto.getDescripcion() != null) existe.setDescripcion(producto.getDescripcion());
        if (producto.getPrecio() != null) existe.setPrecio(producto.getPrecio());

        // actualiza el inventario solo si viene en el body, sin borrar el inventario actual
        if (producto.getInventario() != null     ){   // && !producto.getInventario().isEmpty()) {
            for (var invNuevo : producto.getInventario()) {
                // Buscar si ese color/talle ya existe
                var existente = existe.getInventario().stream()
                    .filter(i -> i.getId().equals(invNuevo.getId()))
                    .findFirst()
                    .orElse(null);

                if (existente!=null) {
                    // actualiza los datos existentes del inventario
                    existente.setStock(invNuevo.getStock());
                    /* // actualiza color y talle dentro del objeto InventarioId PUEDE NO SER NECESARIO
                    invExistente.getId().setColor(invNuevo.getId().getColor());
                    invExistente.getId().setTalle(invNuevo.getId().getTalle()); */
                } else {
                    // si no existe, lo agrega
                    // var nuevo = new Inventario();
                    // nuevo.setId(invNuevo.getId());
                    // nuevo.setStock(invNuevo.getStock());
                    // nuevo.setProducto(existe);
                    // existe.getInventario().add(invNuevo);
                    invNuevo.setProducto(existe);
                    invNuevo.getId().setIdProducto(existe.getIdProducto());
                    existe.getInventario().add(invNuevo);
                }
            }
        }
        // asegurar referencia bidireccional antes de guardar
        existe.getInventario().forEach(inv -> inv.setProducto(existe));

        Producto actualizado = productoService.guardarProducto(existe);
        return ResponseEntity.ok(actualizado);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Integer id) {
        if (productoService.getProductoById(id) == null) {
            return ResponseEntity.notFound().build();
        }

        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }

    
}
