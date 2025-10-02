package dobleyfalta.auth_service.controller;

import dobleyfalta.auth_service.dto.LoginRequest;
import dobleyfalta.auth_service.dto.LoginResponse;
import dobleyfalta.auth_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    // ? Permite que el método devuelva distintos tipos de objetos sin que Java se
    // queje del tipo.
    // Ejemplo:
    // En caso de éxito → ResponseEntity.ok(new LoginResponse(token))
    // En caso de error → ResponseEntity.status(401).body(Map.of(...))
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        var usuario = authService.login(request);

        if (usuario != null) {
            return ResponseEntity.ok(new LoginResponse(usuario.getToken()));
        } else {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    // Map.of Crea un mapa inmutable en Java (clave → valor).
                    // En este caso:
                    // "mensaje" → "Correo o contraseña incorrecta"
                    // "codigoError" → 401
                    // Resultado: un objeto tipo Map<String, Object> con esos pares clave-valor.
                    // Es object para poder usar string, integer o lo que se necesite
                    .body(Map.of(
                            "mensaje", "Correo o contraseña incorrectas",
                            "codigoError", 401));
        }
    }
}
