package com.pablo.japones_app.service;

import com.pablo.japones_app.config.JwtProperties;
import com.pablo.japones_app.dto.EmisionToken;
import com.pablo.japones_app.entity.RefreshToken;
import com.pablo.japones_app.entity.Usuario;
import com.pablo.japones_app.exception.TokenRefreshInvalidoException;
import com.pablo.japones_app.repository.RefreshTokenRepository;
import com.pablo.japones_app.security.JwtService;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HexFormat;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;
    private final JwtProperties jwtProperties;

    @Transactional
    public EmisionToken emitirPara(Usuario usuario) {
        String refreshToken = jwtService.generateRefreshToken(usuario);
        guardar(refreshToken, usuario);
        return new EmisionToken(jwtService.generateAccessToken(usuario), refreshToken);
    }

    @Transactional
    public EmisionToken rotar(String tokenViejo) {
        String email = validarToken(tokenViejo);

        RefreshToken guardado = refreshTokenRepository.findByTokenHash(hash(tokenViejo))
                .orElseThrow(() -> new TokenRefreshInvalidoException("Token de refresh no registrado"));

        if (guardado.getRevocadoEn() != null) {
            revocarTodosDe(email);
            throw new TokenRefreshInvalidoException("Token reutilizado: sesion revocada por posible robo");
        }

        String nuevoToken = jwtService.generateRefreshToken(guardado.getUsuario());
        guardar(nuevoToken, guardado.getUsuario());
        guardado.setRevocadoEn(LocalDateTime.now());
        refreshTokenRepository.save(guardado);

        return new EmisionToken(jwtService.generateAccessToken(guardado.getUsuario()), nuevoToken);
    }

    public void revocarPorToken(String token) {
        try {
            revocarTodosDe(jwtService.extractEmail(token));
        } catch (JwtException ignored) {
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void revocarTodosDe(String email) {
        refreshTokenRepository.findByUsuario_EmailAndRevocadoEnIsNull(email)
                .forEach(t -> t.setRevocadoEn(LocalDateTime.now()));
    }

    private String validarToken(String token) {
        try {
            if (!jwtService.esTokenDeTipoRefresh(token) || jwtService.isTokenExpirado(token)) {
                throw new TokenRefreshInvalidoException("Token de refresh invalido o vencido");
            }
            return jwtService.extractEmail(token);
        } catch (JwtException e) {
            throw new TokenRefreshInvalidoException("Firma del token invalida");
        }
    }

    private void guardar(String token, Usuario usuario) {
        refreshTokenRepository.save(RefreshToken.builder()
                .usuario(usuario)
                .tokenHash(hash(token))
                .expiracion(LocalDateTime.now().plus(Duration.ofMillis(jwtProperties.refreshExpiration())))
                .build());
    }

    private String hash(String token) {
        try {
            byte[] bytes = MessageDigest.getInstance("SHA-256")
                    .digest(token.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(bytes);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 no disponible", e);
        }
    }
}
