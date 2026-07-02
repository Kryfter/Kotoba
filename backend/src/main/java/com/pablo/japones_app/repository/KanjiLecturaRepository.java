package com.pablo.japones_app.repository;

import com.pablo.japones_app.entity.KanjiLectura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KanjiLecturaRepository extends JpaRepository<KanjiLectura, Long> {
    List<KanjiLectura> findByKanjiId(Long kanjiId);
}
