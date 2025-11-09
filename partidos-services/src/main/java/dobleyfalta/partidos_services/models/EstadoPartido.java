package dobleyfalta.partidos_services.models;

public enum EstadoPartido {
    proximo,
    en_vivo,
    terminado;

    // El método estático se puede usar para facilitar la conversión de String a Enum
    // public static EstadoPartido fromString(String text) {
    //     for (EstadoPartido estado : EstadoPartido.values()) {
    //         if (estado.name().equalsIgnoreCase(text.replace(" ", "_"))) {
    //             return estado;
    //         }
    //     }
    //     throw new IllegalArgumentException("Estado de partido no válido: " + text);
    // }
}
