package com.pablo.japones_app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ejemplo")
public class Ejemplo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "oracion_jp", nullable = false)
    private String oracionJp;

    @Column(name = "oracion_traducida_es", nullable = false)
    private String oracionTraducidaEs;

    @Column(name = "oracion_traducida_en", nullable = false)
    private String oracionTraducidaEn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "palabra_id")
    private Palabra palabra;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gramatica_id")
    private Gramatica gramatica;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kanji_id")
    private Kanji kanji;
}
