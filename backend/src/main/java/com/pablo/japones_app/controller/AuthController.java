package com.pablo.japones_app.controller;

import com.pablo.japones_app.config.JwtProperties;
import com.pablo.japones_app.dto.EmisionToken;
import com.pablo.japones_app.exception.TokenRefreshInvalidoException;
import com.pablo.japones_app.service.RefreshTokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final String COOKIE_REFRESH = "refresh_token";
    private static final String COOKIE_ACCESS = "access_token";

    private final RefreshTokenService refreshTokenService;
    private final JwtProperties jwtProperties;

    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> refresh(HttpServletRequest request, HttpServletResponse response) {
        String tokenViejo = extraerCookie(request, COOKIE_REFRESH);
        if (tokenViejo == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Falta la cookie de refresh"));
        }

        try {
            EmisionToken emision = refreshTokenService.rotar(tokenViejo);
            response.addHeader(HttpHeaders.SET_COOKIE, cookie(COOKIE_REFRESH, emision.refreshToken(),
                    Duration.ofMillis(jwtProperties.refreshExpiration())).toString());
            return ResponseEntity.ok(Map.of("accessToken", emision.accessToken()));
        } catch (TokenRefreshInvalidoException e) {
            response.addHeader(HttpHeaders.SET_COOKIE, cookie(COOKIE_REFRESH, "", Duration.ZERO).toString());
            return ResponseEntity.status(401).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpServletRequest request, HttpServletResponse response) {
        String token = extraerCookie(request, COOKIE_REFRESH);
        if (token != null) {
            refreshTokenService.revocarPorToken(token);
        }
        response.addHeader(HttpHeaders.SET_COOKIE, cookie(COOKIE_REFRESH, "", Duration.ZERO).toString());
        response.addHeader(HttpHeaders.SET_COOKIE, cookie(COOKIE_ACCESS, "", Duration.ZERO).toString());
        return ResponseEntity.ok(Map.of("mensaje", "Sesion cerrada"));
    }

    private String extraerCookie(HttpServletRequest request, String nombre) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (nombre.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    private ResponseCookie cookie(String nombre, String valor, Duration maxAge) {
        return ResponseCookie.from(nombre, valor)
                .httpOnly(true)
                .secure(true)
                .sameSite("Lax")
                .path("/")
                .maxAge(maxAge)
                .build();
    }
}
