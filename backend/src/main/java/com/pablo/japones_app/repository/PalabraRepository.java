package com.pablo.japones_app.repository;

import com.pablo.japones_app.entity.Palabra;
import com.pablo.japones_app.enums.NivelJlpt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PalabraRepository extends JpaRepository<Palabra, Long> {
    Optional<Palabra> findByPalabraAndLectura(String palabra, String lectura);
    List<Palabra> findByNivelJlpt(NivelJlpt nivelJlpt);
    List<Palabra> findByNivelJlptOrderByOrdenLeccion(NivelJlpt nivel);

}
