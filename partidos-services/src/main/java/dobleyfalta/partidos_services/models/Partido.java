package dobleyfalta.partidos_services.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "partido")
public class Partido {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_partido")
    private Integer idPartido;

    @Column(name = "fecha")
    private LocalDateTime fecha;

    @Column(name = "puntos_local")
    private Integer puntosLocal;

    @Column(name = "puntos_visitante")
    private Integer puntosVisitante;

    @Column(name = "id_equipo_local")
    private Integer idEquipoLocal;
    
    @Column(name = "id_equipo_visitante")
    private Integer idEquipoVisitante;
    
    @ManyToOne
    @JoinColumn(name = "id_jornada")
    private Jornada jornada;

    @Enumerated(EnumType.STRING) // Le dice a JPA que almacene el nombre del enum como string
    @Column(name = "estado", nullable = false) // Mapea a la columna 'estado' de la DB
    private EstadoPartido estadoPartido;
}
