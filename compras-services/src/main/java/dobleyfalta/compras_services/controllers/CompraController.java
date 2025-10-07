package dobleyfalta.compras_services.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dobleyfalta.compras_services.DTO.CompraDTO;
import dobleyfalta.compras_services.models.Compra;
import dobleyfalta.compras_services.services.CompraService;


@RestController
@RequestMapping("/api/compras")
public class CompraController {
    
    private final CompraService compraService;

    public CompraController(CompraService compraService) {
        this.compraService = compraService;
    }


   @GetMapping("/{id}")
    public ResponseEntity<CompraDTO> getCompraDetallada(@PathVariable Integer id) {
        CompraDTO compra = compraService.getCompraDetallada(id);
        if (compra != null) {
            return ResponseEntity.ok(compra);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Compra>> getAllCompras() {
        List<Compra> compras = compraService.getAllCompras();
        return ResponseEntity.ok(compras);
    }

    @PostMapping("/add")
    public ResponseEntity<CompraDTO> createCompra(@RequestBody CompraDTO compraDTO) {
        if (compraDTO == null) {
            return ResponseEntity.badRequest().build();
        }
        CompraDTO nuevaCompra = compraService.createCompra(compraDTO);
        return ResponseEntity.ok(nuevaCompra);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompra(@PathVariable Integer id) {
        boolean deleted = compraService.deleteCompra(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
