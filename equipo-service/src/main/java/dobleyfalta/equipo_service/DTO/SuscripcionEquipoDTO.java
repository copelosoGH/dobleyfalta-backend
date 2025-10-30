package dobleyfalta.equipo_service.DTO;

import java.sql.Date;

public class SuscripcionEquipoDTO {
    private Integer idUsuario;
    private Integer idEquipo;
    private Date fechaSuscripcion;
    private UsuarioDTO usuario;
    private EquipoDTO equipo;

    // Getters y setters
    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public Integer getIdEquipo() { return idEquipo; }
    public void setIdEquipo(Integer idEquipo) { this.idEquipo = idEquipo; }

    public Date getFechaSuscripcion() { return fechaSuscripcion; }
    public void setFechaSuscripcion(Date fechaSuscripcion) { this.fechaSuscripcion = fechaSuscripcion; }

    public UsuarioDTO getUsuario() { return usuario; }
    public void setUsuario(UsuarioDTO usuario) { this.usuario = usuario; }

    public EquipoDTO getEquipo() { return equipo; }
    public void setEquipo(EquipoDTO equipo) { this.equipo = equipo; }
}
