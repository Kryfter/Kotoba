package com.pablo.japones_app.repository;

import com.pablo.japones_app.entity.Gramatica;
import com.pablo.japones_app.enums.NivelJlpt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GramaticaRepository extends JpaRepository<Gramatica, Long> {
    Optional<Gramatica> findByPatron(String patron);
    List<Gramatica> findByNivelJlpt(NivelJlpt nivelJlpt);
    List<Gramatica> findByNivelJlptOrderByOrdenLeccion(NivelJlpt nivel);
}
