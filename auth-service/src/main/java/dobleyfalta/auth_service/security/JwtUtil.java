package dobleyfalta.auth_service.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    // Genera un Key a partir de un String
    private final Key SECRET_KEY = Keys.hmacShaKeyFor("miClaveSuperSecretaMuyLargaParaHS256!".getBytes());
    private final long EXPIRATION = 1000 * 60 * 60; // 1 hora

    public String generateToken(String correo) {
        return Jwts.builder()
                .setSubject(correo)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256) // usa Key
                .compact();
    }

    public String getCorreo(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY) // usa Key
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}