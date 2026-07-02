package com.pablo.japones_app.repository;

import com.pablo.japones_app.entity.Suscripcion;
import com.pablo.japones_app.enums.EstadoSuscripcion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SuscripcionRepository extends JpaRepository<Suscripcion, Long> {
    List<Suscripcion> findByUsuarioId(Long usuarioId);
    Optional<Suscripcion> findByUsuarioIdAndEstado(Long usuarioId, EstadoSuscripcion estado);
}
