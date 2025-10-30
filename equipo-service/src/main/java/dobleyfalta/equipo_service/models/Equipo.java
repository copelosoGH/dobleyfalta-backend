package dobleyfalta.equipo_service.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "equipo")
public class Equipo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_equipo")
    private Integer idEquipo;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "ciudad")
    private String ciudad;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "logo")
    private String logo;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "id_liga")
    private Integer idLiga;  // relación con la tabla LIGA (solo el id)
}
