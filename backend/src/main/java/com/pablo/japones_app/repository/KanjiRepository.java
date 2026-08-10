package com.pablo.japones_app.repository;

import com.pablo.japones_app.entity.Kanji;
import com.pablo.japones_app.enums.NivelJlpt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface KanjiRepository extends JpaRepository<Kanji, Long> {
    Optional<Kanji> findByCaracter(String caracter);
    List<Kanji> findByNivelJlpt(NivelJlpt nivelJlpt);
    List<Kanji> findByNivelJlptOrderByOrdenLeccion(NivelJlpt nivel);

}
