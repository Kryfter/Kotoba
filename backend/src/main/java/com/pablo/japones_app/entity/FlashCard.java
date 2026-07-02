package com.pablo.japones_app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "flashcard")
public class FlashCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creador_id")
    private Usuario creador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kanji_id")
    private Kanji kanji;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kana_id")
    private Kana kana;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gramatica_id")
    private Gramatica gramatica;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "palabra_id")
    private Palabra palabra;

    @Column(name = "frente_custom")
    private String frenteCustom;

    @Column(name = "dorso_custom")
    private String dorsoCustom;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @OneToMany(mappedBy = "flashCard")
    private List<ProgresoUsuario> progresos = new ArrayList<>();
}
