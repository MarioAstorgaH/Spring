package com.spring.crud.config;


import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    static Dotenv dotenv= Dotenv.load();
    private static final String SECRET_KEY=dotenv.get("SECRET_KEY");

    public String getToken(UserDetails user) {
        return getToken(new HashMap<>(), user);
    }
    public String getToken(Map<String, Object> extraClaims, UserDetails user) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24 horas de vida
                .signWith(getKey())
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Claims getAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(generateSafeToken());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Truco para que no tengas errores de longitud de clave en desarrollo
    // En PROD usa una clave real en Base64 en el application.properties
    private String generateSafeToken() {
        // Esto es solo para que funcione el ejemplo con la clave simple de arriba.
        // Convierte tu texto plano en algo compatible con Base64.
        return java.util.Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
    }
}
