package dobleyfalta.auth_service.client;

import dobleyfalta.auth_service.model.Usuario;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UsuarioClient {

    private final RestTemplate restTemplate;
    private static final String BASE_URL = "http://localhost:8080";

    public UsuarioClient() {
        this.restTemplate = new RestTemplate();
    }

    public Usuario getByCorreo(String correo) {
        return restTemplate.getForObject(BASE_URL + "/api/usuarios/correo/{correo}", Usuario.class, correo);
    }
}
