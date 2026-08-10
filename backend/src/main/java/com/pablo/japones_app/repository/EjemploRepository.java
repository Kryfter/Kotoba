package com.pablo.japones_app.repository;

import com.pablo.japones_app.entity.Ejemplo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EjemploRepository extends JpaRepository<Ejemplo, Long> {
    List<Ejemplo> findByPalabraId(Long palabraId);
    List<Ejemplo> findByGramaticaId(Long gramaticaId);
    List<Ejemplo> findByKanjiId(Long kanjiId);
}
