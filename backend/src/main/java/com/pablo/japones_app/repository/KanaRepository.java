package com.pablo.japones_app.repository;

import com.pablo.japones_app.entity.Kana;
import com.pablo.japones_app.enums.TipoKana;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface KanaRepository extends JpaRepository<Kana, Long> {
    Optional<Kana> findByCaracterAndTipoKana(String caracter, TipoKana tipoKana);
    List<Kana> findByTipoKanaOrderByOrdenLeccion(TipoKana tipo);
}
