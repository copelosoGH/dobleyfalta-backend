package dobleyfalta.compras_services.controllers;

import org.springframework.web.bind.annotation.RestController;

import dobleyfalta.compras_services.models.Carrito;
import dobleyfalta.compras_services.services.CarritoService;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/carrito")
public class CarritoController {

    private final CarritoService carritoService;

    public CarritoController(CarritoService carritoService) {
        this.carritoService = carritoService;
    }

    @GetMapping("/all")
    public List<Carrito> getAllCarritos() {
        return carritoService.getCarritoRepository();
    }
    
}
