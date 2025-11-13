package dobleyfalta.partidos_services.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import dobleyfalta.partidos_services.DTO.TablaDTO;
import dobleyfalta.partidos_services.services.TablaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/tablas")
public class TablaController {
    private final TablaService tablaService;

    public TablaController(TablaService tablaService) {
        this.tablaService = tablaService;
    }

    @GetMapping("/liga/{idLiga}")
    public ResponseEntity<List<TablaDTO>> getTablaPorLiga(@PathVariable Integer idLiga) {
        List<TablaDTO> tabla = tablaService.calcularTablaPorLiga(idLiga);
        return ResponseEntity.ok(tabla);
    }

}