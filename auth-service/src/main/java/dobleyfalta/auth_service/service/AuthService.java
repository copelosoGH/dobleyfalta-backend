package dobleyfalta.auth_service.service;

import dobleyfalta.auth_service.client.UsuarioClient;
import dobleyfalta.auth_service.dto.LoginRequest;
import dobleyfalta.auth_service.dto.LoginResponse;
import dobleyfalta.auth_service.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioClient usuarioClient;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(UsuarioClient usuarioClient, JwtUtil jwtUtil) {
        this.usuarioClient = usuarioClient;
        this.jwtUtil = jwtUtil;
    }

    public LoginResponse login(LoginRequest request) {
        var usuario = usuarioClient.getByCorreo(request.getCorreo());

        if (passwordEncoder.matches(request.getContrasena(), usuario.getContrasena())) {
            String token = jwtUtil.generateToken(usuario.getCorreo());
            return new LoginResponse(token);
        } else {
            return null;
        }
    }
}