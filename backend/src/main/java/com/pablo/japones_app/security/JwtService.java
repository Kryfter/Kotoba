package com.pablo.japones_app.security;

import com.pablo.japones_app.config.JwtProperties;
import com.pablo.japones_app.entity.Usuario;
import com.pablo.japones_app.enums.Rol;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtProperties jwtProperties;

    public String generateAccessToken(Usuario usuario) {
        Set<String> roles = usuario.getRoles().stream().map(Enum::name).collect(Collectors.toSet());
        return Jwts.builder()
                .subject(usuario.getEmail())
                .claim("tipo", "access")
                .claim("roles", roles)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtProperties.expiration()))
                .signWith(getSignKey())
                .compact();
    }

    public String generateRefreshToken(Usuario usuario) {
        return Jwts.builder()
                .subject(usuario.getEmail())
                .claim("tipo", "refresh")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtProperties.refreshExpiration()))
                .signWith(getSignKey())
                .compact();
    }

    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String email = extractEmail(token);
        return email.equals(userDetails.getUsername()) && !isTokenExpirado(token);
    }

    public boolean isTokenExpirado(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    public boolean esTokenDeTipoAccess(String token) {
        return "access".equals(extractAllClaims(token).get("tipo", String.class));
    }

    public boolean esTokenDeTipoRefresh(String token) {
        return "refresh".equals(extractAllClaims(token).get("tipo", String.class));
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(getSignKey()).build().parseSignedClaims(token).getPayload();
    }

    private SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.secret());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
