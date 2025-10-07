package dobleyfalta.compras_services.controllers;

import org.springframework.web.bind.annotation.RestController;

import dobleyfalta.compras_services.models.Carrito;
import dobleyfalta.compras_services.services.CarritoService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/carrito")
public class CarritoController {

    private final CarritoService carritoService;

    public CarritoController(CarritoService carritoService) {
        this.carritoService = carritoService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Carrito>> getAllCarritos() {
        return ResponseEntity.ok(carritoService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Carrito> getCarritoById(@PathVariable Integer id) {
       if (id == null) {
           return ResponseEntity.notFound().build();
       }
       return ResponseEntity.ok(carritoService.getCarritoById(id));
       
    }
    
    @PostMapping("/add")
    public ResponseEntity<Carrito> addCarrito(@RequestBody Carrito carrito) {
        if (carrito == null) {
            return ResponseEntity.badRequest().build();
        }
        Carrito newCarrito = carritoService.crearCarrito(carrito);
        return ResponseEntity.ok(newCarrito);
    }
 
    @PutMapping("/{id}")
    public ResponseEntity<Carrito> editarCarrito(@PathVariable Integer id, @RequestBody Carrito carrito) {
        if (id == null || carrito == null) {
            return ResponseEntity.notFound().build();
        }
        Carrito actualizarCarrito = carritoService.editarCarrito(id, carrito);
        return ResponseEntity.ok(actualizarCarrito);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCarrito(@PathVariable Integer id) {
        boolean eliminado = carritoService.eliminarCarrito(id);
        if (eliminado) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}