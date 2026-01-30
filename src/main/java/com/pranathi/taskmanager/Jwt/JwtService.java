package com.pranathi.taskmanager.Jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email) // WHO this token belongs to
                .setIssuedAt(new Date()) // WHEN token was created
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // Expiry (1 hour)
                .signWith(SECRET_KEY) // SIGN token securely
                .compact(); // Build final token string
    }
    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

}