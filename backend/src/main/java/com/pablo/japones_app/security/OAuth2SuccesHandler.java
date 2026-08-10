package com.pablo.japones_app.security;

import com.pablo.japones_app.config.JwtProperties;
import com.pablo.japones_app.dto.EmisionToken;
import com.pablo.japones_app.entity.Usuario;
import com.pablo.japones_app.service.CuentaAutenticacionService;
import com.pablo.japones_app.service.RefreshTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;

@Component
@RequiredArgsConstructor
public class OAuth2SuccesHandler implements AuthenticationSuccessHandler {

    private final CuentaAutenticacionService cuentaService;
    private final RefreshTokenService refreshTokenService;
    private final JwtProperties jwtProperties;

    @Value("${app.frontend-url:}")
    private String frontendUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
        String email = oauthUser.getAttribute("email");
        Boolean emailVerificado = oauthUser.getAttribute("email_verified");
        String nombre = oauthUser.getAttribute("name");

        Usuario usuario = cuentaService.findOrCreateFromGoogle(email, nombre, emailVerificado);
        EmisionToken emision = refreshTokenService.emitirPara(usuario);

        if (frontendUrl.isBlank()) {
            responderConJson(response, emision);
        } else {
            setAccessTokenCookie(response, emision.accessToken());
            setRefreshTokenCookie(response, emision.refreshToken());
            response.sendRedirect(frontendUrl + "/oauth2/callback");
        }
    }

    private void responderConJson(HttpServletResponse response, EmisionToken emision) throws IOException {
        response.setContentType("application/json");
        response.getWriter().write("{\"accessToken\": \"" + emision.accessToken()
                + "\", \"refreshToken\": \"" + emision.refreshToken() + "\"}");
    }

    private void setAccessTokenCookie(HttpServletResponse response, String token) {
        ResponseCookie cookie = ResponseCookie.from("access_token", token)
                .httpOnly(true)
                .secure(true)
                .sameSite("Lax")
                .path("/")
                .maxAge(Duration.ofMillis(jwtProperties.expiration()))
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    private void setRefreshTokenCookie(HttpServletResponse response, String token) {
        ResponseCookie cookie = ResponseCookie.from("refresh_token", token)
                .httpOnly(true)
                .secure(true)
                .sameSite("Lax")
                .path("/")
                .maxAge(Duration.ofMillis(jwtProperties.refreshExpiration()))
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }
}
