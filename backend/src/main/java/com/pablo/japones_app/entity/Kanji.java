package com.pablo.japones_app.entity;

import com.pablo.japones_app.enums.NivelJlpt;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "kanji")
public class Kanji {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "caracter", nullable = false, unique = true)
    private String caracter;

    @Column(name = "significado_es", nullable = false)
    private String significadoEs;

    @Column(name = "significado_en", nullable = false)
    private String significadoEn;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "nivel_jlpt", nullable = false)
    private NivelJlpt nivelJlpt;

    @Column(name = "numero_trazos")
    private Integer numeroTrazos;

    @Column(name = "audio_url")
    private String audioUrl;

    @Column(name = "stroke_order_url")
    private String strokeOrderUrl;

    @Column(name = "orden_leccion")
    private Integer ordenLeccion;

    // Relacion inversa con KanjiLectura
    @OneToMany(mappedBy = "kanji", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<KanjiLectura> lecturas = new ArrayList<>();

    @OneToMany(mappedBy = "kanji")
    private List<Ejemplo> ejemplos = new ArrayList<>();

    @OneToMany(mappedBy = "kanji")
    private List<FlashCard> flashcards = new ArrayList<>();
}
