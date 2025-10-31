package dobleyfalta.equipo_service.DTO;

import java.sql.Date;

public class SuscripcionEquipoDTO {
    private UsuarioDTO usuario;
    private EquipoDTO equipo;
    private Date fechaSuscripcion;

    public Date getFechaSuscripcion() { return fechaSuscripcion; }
    public void setFechaSuscripcion(Date fechaSuscripcion) { this.fechaSuscripcion = fechaSuscripcion; }

    public UsuarioDTO getUsuario() { return usuario; }
    public void setUsuario(UsuarioDTO usuario) { this.usuario = usuario; }

    public EquipoDTO getEquipo() { return equipo; }
    public void setEquipo(EquipoDTO equipo) { this.equipo = equipo; }
}
