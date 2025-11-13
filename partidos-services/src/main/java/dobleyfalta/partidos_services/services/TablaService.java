package dobleyfalta.partidos_services.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import dobleyfalta.partidos_services.DTO.EquipoDTO;
import dobleyfalta.partidos_services.DTO.TablaDTO;
import dobleyfalta.partidos_services.models.Partido;
import dobleyfalta.partidos_services.repository.PartidoRespository;


@Service
public class TablaService {
    
    private final PartidoRespository partidoRepository;     
    private final RestTemplate restTemplate; // para consultar los equipos desde equipo-service
    private final String EQUIPO_SERVICE_URL = "http://localhost:8080/api/equipos/";

    public TablaService(PartidoRespository partidoRepository, RestTemplate restTemplate) {
        this.partidoRepository = partidoRepository;
        this.restTemplate = restTemplate;
    }

    public List<TablaDTO> calcularTablaPorLiga(Integer idLiga) {
        List<Partido> partidos = partidoRepository.findByJornada_Liga_IdLiga(idLiga);

        Map<Integer, TablaDTO> tabla = new HashMap<>();

        for(Partido partido : partidos){
            if (partido.getEstadoPartido() == null || !partido.getEstadoPartido().equals(dobleyfalta.partidos_services.models.EstadoPartido.terminado)){
                continue; // solo considerar partidos terminados
            }

            Integer idEquipoLocal = partido.getIdEquipoLocal();
            Integer idEquipoVisitante = partido.getIdEquipoVisitante();
            
            //obtener o crear entrada para equipo local
            tabla.putIfAbsent(idEquipoLocal, new TablaDTO(null, getLogo(idEquipoLocal), getNombreEquipo(idEquipoLocal), 0,0,0,0,0,0));
            tabla.putIfAbsent(idEquipoVisitante, new TablaDTO(null, getLogo(idEquipoVisitante), getNombreEquipo(idEquipoVisitante), 0,0,0,0,0,0));
        
            TablaDTO equipoLocal = tabla.get(idEquipoLocal);
            TablaDTO equipoVisitante = tabla.get(idEquipoVisitante);

            // actualizar partidos jugados (PJ)
            equipoLocal.setPj(equipoLocal.getPj() + 1);
            equipoVisitante.setPj(equipoVisitante.getPj() + 1);

            // actualizar puntos a favor (PF) y en contra (PC)
            equipoLocal.setPf(equipoLocal.getPf() + partido.getPuntosLocal());
            equipoLocal.setPc(equipoLocal.getPc() + partido.getPuntosVisitante());
            equipoVisitante.setPf(equipoVisitante.getPf() + partido.getPuntosVisitante());
            equipoVisitante.setPc(equipoVisitante.getPc() + partido.getPuntosLocal());

            // actualizar partidos ganados (PG) y perdidos (PP) y los puntos totales (puntos)
            if(partido.getPuntosLocal() > partido.getPuntosVisitante()){
                equipoLocal.setPg(equipoLocal.getPg() + 1);
                equipoVisitante.setPp(equipoVisitante.getPp() + 1);
                equipoLocal.setPuntos(equipoLocal.getPuntos() + 2); 
                equipoVisitante.setPuntos(equipoVisitante.getPuntos() + 1);
            } else if(partido.getPuntosLocal() < partido.getPuntosVisitante()){
                equipoVisitante.setPg(equipoVisitante.getPg() + 1);
                equipoLocal.setPp(equipoLocal.getPp() + 1);
                equipoVisitante.setPuntos(equipoVisitante.getPuntos() + 2);
                equipoLocal.setPuntos(equipoLocal.getPuntos() + 1);
            }else{
                //en caso de empate (Aunque no debería ocurrir) 
                equipoLocal.setPuntos(equipoLocal.getPuntos() + 1);
                equipoVisitante.setPuntos(equipoVisitante.getPuntos() + 1);
                // en caso de empate se define al ganador dependiendo porcentaje de victorias o diferencia de puntos
                /* if(equipoLocal.getPuntos() > equipoVisitante.getPuntos(){
                    equipoLocal.setPg(equipoLocal.getPg() + 1);
                    equipoVisitante.setPp(equipoVisitante.getPp() + 1);
                    equipoLocal.setPuntos(equipoLocal.getPuntos() + 2);
                    equipoVisitante.setPuntos(equipoVisitante.getPuntos() + 1);
                }else{
                    equipoVisitante.setPg(equipoVisitante.getPg() + 1);
                    equipoLocal.setPp(equipoLocal.getPp() + 1);
                    equipoVisitante.setPuntos(equipoVisitante.getPuntos() + 2);
                    equipoLocal.setPuntos(equipoLocal.getPuntos() + 1);
                } */
            }
        }

        // Convertir el mapa a lista y ordenar por puntos
        List<TablaDTO> listaTabla = new ArrayList<>(tabla.values());
        listaTabla.sort(Comparator.comparing(TablaDTO::getPuntos).reversed()
                .thenComparing(td -> td.getPf() - td.getPc(), Comparator.reverseOrder())); // desempate por diferencia de puntos

        // Asignar puestos
        int puesto = 1;
        for (TablaDTO equipo : listaTabla) {
            equipo.setPuesto(puesto++);
        }

        return listaTabla;
    }

    private String getNombreEquipo(Integer idEquipo) {
        try{
            EquipoDTO equipo = restTemplate.getForObject(EQUIPO_SERVICE_URL + idEquipo, EquipoDTO.class);
            return equipo != null ? equipo.getNombre() : "Equipo"+idEquipo;
        } catch (Exception e){
            return "Equipo"+idEquipo;
        }
    }

    private String getLogo(Integer idEquipo) {
        try{
            EquipoDTO equipo = restTemplate.getForObject(EQUIPO_SERVICE_URL + idEquipo, EquipoDTO.class);
            return equipo != null ? equipo.getLogo() : "Logo"+idEquipo;
        } catch (Exception e){
            return "Logo"+idEquipo;
        }
    }
}