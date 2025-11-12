package dobleyfalta.partidos_services.DTO;

import dobleyfalta.partidos_services.models.EstadoPartido;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor; // Añadido para asegurar la creación del objeto
import lombok.AllArgsConstructor; // Añadido (opcional)

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrearPartidoRequestDTO {
    
    // El ID simple, como lo quieres recibir desde el frontend
    private Integer idJornada; 

    private LocalDateTime fecha;
    private Integer puntosLocal = 0;
    private Integer puntosVisitante = 0;
    private Integer idEquipoLocal;
    private Integer idEquipoVisitante;
    
    // Usamos el Enum directamente. Spring lo mapea desde el string del JSON.
    private EstadoPartido estadoPartido; 
}