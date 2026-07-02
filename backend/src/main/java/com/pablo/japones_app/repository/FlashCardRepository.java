package com.pablo.japones_app.repository;

import com.pablo.japones_app.entity.FlashCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlashCardRepository extends JpaRepository<FlashCard, Long> {
    List<FlashCard> findByCreadorId(Long usuarioId);
    List<FlashCard> findByCreadorIsNull();
}
