package dobleyfalta.compras_services.services;

import java.util.ArrayList;
import java.util.List;

// import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import dobleyfalta.compras_services.DTO.CompraDTO;
import dobleyfalta.compras_services.DTO.DetalleCarritoDTO;
import dobleyfalta.compras_services.DTO.ProductoDTO;
import dobleyfalta.compras_services.models.Carrito;
import dobleyfalta.compras_services.models.Compra;
import dobleyfalta.compras_services.models.DetalleCarrito;
import dobleyfalta.compras_services.repository.CompraRepository;

@Service
public class CompraService {

    private final CompraRepository compraRepository;
    private final RestTemplate restTemplate;
    private static final String PRODUCTOS_URL = "http://localhost:8080/api/producto";

    public CompraService(CompraRepository compraRepository, RestTemplate restTemplate) {
        this.compraRepository = compraRepository;
        this.restTemplate = restTemplate;
    }

    public List<Compra> getAllCompras() {
        return compraRepository.findAll();
    }

    public CompraDTO getCompraDetallada(Integer idCompra) {
        Compra compra = compraRepository.findByIdCompra(idCompra);
        if (compra == null) {
            return null;
        }

        Carrito carrito = compra.getCarrito();
        List<DetalleCarritoDTO> detalleDTOs = new ArrayList<>();
        float total = 0;

        for (DetalleCarrito detalle : carrito.getDetalles()) {
            Integer idProducto = detalle.getIdDetalleCarrito().getIdProducto(); // Obtener ID del producto a traves del
                                                                                // detalle del carrito

            ProductoDTO producto = restTemplate.getForObject(
                    PRODUCTOS_URL + "/" + idProducto,
                    ProductoDTO.class);

            if (producto != null) {
                total += producto.getPrecio() * detalle.getCantidad();
                detalleDTOs.add(new DetalleCarritoDTO(producto, detalle.getCantidad()));
            }
        }

        // Armar la respuesta final
        CompraDTO compraDTO = new CompraDTO();
        compraDTO.setIdCompra(compra.getIdCompra());
        compraDTO.setFechaCompra(compra.getFechaCompra());
        compraDTO.setMontoTotal(total);
        compraDTO.setMetodoPago(compra.getMetodoPago());
        compraDTO.setIdCarrito(carrito.getIdCarrito());
        compraDTO.setIdUsuario(carrito.getIdUsuario());
        compraDTO.setFechaCreacion(carrito.getFechaCreacion());
        compraDTO.setDetalles(detalleDTOs);

        return compraDTO;
    }

    public CompraDTO createCompra(CompraDTO compraDTO) {
        if (compraDTO == null) {
            return null;
        }

        Compra compra = new Compra();
        compra.setFechaCompra(compraDTO.getFechaCompra());
        compra.setMetodoPago(compraDTO.getMetodoPago());
        compra.setMontoTotal(compraDTO.getMontoTotal());

        Carrito carrito = new Carrito();
        carrito.setIdCarrito(compraDTO.getIdCarrito());
        carrito.setIdUsuario(compraDTO.getIdUsuario());
        carrito.setFechaCreacion(compraDTO.getFechaCreacion());

        compra.setCarrito(carrito);

        Compra nuevaCompra = compraRepository.save(compra);

        compraDTO.setIdCompra(nuevaCompra.getIdCompra());
        return compraDTO;
    }

    public boolean deleteCompra(Integer idCompra) {
        Compra compra = compraRepository.findByIdCompra(idCompra);
        if (compra != null) {
            compraRepository.delete(compra);
            return true;
        }
        return false;
    }

}