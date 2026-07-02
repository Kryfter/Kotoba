package com.pablo.japones_app.entity;

import com.pablo.japones_app.enums.TipoKana;
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
@Table(name = "kana", uniqueConstraints = {
        @UniqueConstraint(name = "uq_kana_caracter_tipo", columnNames = {"caracter", "tipo"})
})
public class Kana {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "caracter", nullable = false)
    private String caracter;

    @Column(name = "romaji", nullable = false)
    private String romaji;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "tipo", nullable = false)
    private TipoKana tipoKana;

    @Column(name = "audio_url")
    private String audioUrl;

    @Column(name = "stroke_order_url")
    private String strokeOrderUrl;

    @Column(name = "orden_leccion")
    private Integer ordenLeccion;

    @OneToMany(mappedBy = "kana")
    private List<FlashCard> flashcards = new ArrayList<>();
}
