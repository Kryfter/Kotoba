package com.pablo.japones_app.service;

import com.pablo.japones_app.entity.CuentaAutenticacion;
import com.pablo.japones_app.entity.Usuario;
import com.pablo.japones_app.repository.CuentaAutenticacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final CuentaAutenticacionRepository cuentaAutenticacionRepository;

    @Override
    public UserDetails loadUserByUsername(String email){
        CuentaAutenticacion cuenta = cuentaAutenticacionRepository
                .findFirstByUsuarioEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));

        Usuario usuario = cuenta.getUsuario();

        return User.builder()
                .username(usuario.getEmail())
                .password(cuenta.getPasswordHash())
                .authorities(
                        usuario.getRoles().stream()
                                .map(r -> new SimpleGrantedAuthority("ROLE_" + r.name()))
                                .toList()
                )
                .build();
    }

}
