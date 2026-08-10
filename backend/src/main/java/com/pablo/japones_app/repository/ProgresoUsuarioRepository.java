package com.pablo.japones_app.repository;

import com.pablo.japones_app.entity.ProgresoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProgresoUsuarioRepository extends JpaRepository<ProgresoUsuario, Long> {
    List<ProgresoUsuario> findByUsuarioIdAndProximaRevisionLessThanEqual(Long usuarioId, LocalDate fecha);
    Optional<ProgresoUsuario> findByUsuarioIdAndFlashCardId(Long usuarioId, Long flashcardId);

}
