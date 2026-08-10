package com.pablo.japones_app.service;

import com.pablo.japones_app.entity.CuentaAutenticacion;
import com.pablo.japones_app.entity.Usuario;
import com.pablo.japones_app.enums.ProveedorAuth;
import com.pablo.japones_app.enums.Rol;
import com.pablo.japones_app.repository.CuentaAutenticacionRepository;
import com.pablo.japones_app.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class CuentaAutenticacionService {

    private final UsuarioRepository usuarioRepository;
    private final CuentaAutenticacionRepository cuentaRepository;

    @Transactional
    public Usuario findOrCreateFromGoogle(String email, String nombre, Boolean emailVerificado){

        if (!Boolean.TRUE.equals(emailVerificado)){
            throw new OAuth2AuthenticationException("Email no verificado por Google");
        }
        return usuarioRepository.findByEmail(email)
                .orElseGet(() -> crearUsuarioDesdeGoogle(email, nombre));
    }

    private Usuario crearUsuarioDesdeGoogle(String email, String nombre){
        Usuario usuario = usuarioRepository.save(
                Usuario.builder().email(email).nombre(nombre).roles(Set.of(Rol.USER)).build()
        );
        cuentaRepository.save(
                CuentaAutenticacion.builder()
                        .usuario(usuario)
                        .proveedor(ProveedorAuth.GOOGLE)
                        .passwordHash(null)
                        .build()
        );
        return usuario;
    }
}
