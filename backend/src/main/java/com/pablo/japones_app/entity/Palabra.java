package com.pablo.japones_app.entity;

import com.pablo.japones_app.enums.NivelJlpt;
import com.pablo.japones_app.enums.TipoPalabra;
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
@Table(name = "palabra", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"palabra", "lectura"})
})
public class Palabra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "palabra", nullable = false)
    private String palabra;

    @Column(name = "lectura", nullable = false)
    private String lectura;

    @Column(name = "significado_es", nullable = false)
    private String significadoEs;

    @Column(name = "significado_en", nullable = false)
    private String significadoEn;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "nivel_jlpt", nullable = false)
    private NivelJlpt nivelJlpt;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "tipo_palabra", nullable = false)
    private TipoPalabra tipoPalabra;

    @Column(name = "orden_leccion")
    private Integer ordenLeccion;

    @OneToMany(mappedBy = "palabra")
    private List<Ejemplo> ejemplos = new ArrayList<>();

    @OneToMany(mappedBy = "palabra")
    private List<FlashCard> flashcards = new ArrayList<>();
}
