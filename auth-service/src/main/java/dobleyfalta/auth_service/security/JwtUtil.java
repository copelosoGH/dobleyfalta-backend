package dobleyfalta.auth_service.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    // Genera un Key a partir de un String
    private final Key SECRET_KEY = Keys.hmacShaKeyFor("conradoPelosoNaomiKakisuYEmanuelNeme".getBytes());
    private final long EXPIRATION = 1000 * 60 * 60; // 1 hora

    public String generateToken(String correo) {
        return Jwts.builder()
                .setSubject(correo)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    // se usa para leer el correo guardado dentro de un JWT (el subject del token enviado por el cliente).
    // sirve para extraer la identidad (correo) del usuario autenticado desde el token JWT y 
    // poder usarlo en tu lógica (ej: cargar su perfil, validar sus permisos).
    public String getCorreo(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}