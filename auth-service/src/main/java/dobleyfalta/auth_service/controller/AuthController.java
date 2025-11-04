package dobleyfalta.auth_service.controller;

import dobleyfalta.auth_service.dto.LoginRequest;
import dobleyfalta.auth_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("/api/auth")
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
        try {
            var response = authService.login(request);

            if (response != null) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of(
                                "mensaje", "Correo o contraseña incorrectas",
                                "codigoError", 401));
            }

        } catch (HttpClientErrorException.NotFound e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of(
                            "mensaje", "Correo o contraseña incorrectas",
                            "codigoError", 401));
        }
    }
}
