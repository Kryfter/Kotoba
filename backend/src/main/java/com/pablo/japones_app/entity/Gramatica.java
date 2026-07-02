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
@Table(name = "gramatica", uniqueConstraints = {
        @UniqueConstraint(columnNames = "patron")
})
public class Gramatica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "patron", nullable = false)
    private String patron;

    @Column(name = "explicacion_es", nullable = false)
    private String explicacionEs;

    @Column(name = "explicacion_en", nullable = false)
    private String explicacionEn;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "nivel_jlpt", nullable = false)
    private NivelJlpt nivelJlpt;

    @Column(name = "orden_leccion")
    private Integer ordenLeccion;

    @OneToMany(mappedBy = "gramatica", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ejemplo> ejemplos = new ArrayList<>();

    @OneToMany(mappedBy = "gramatica")
    private List<FlashCard> flashcards = new ArrayList<>();

}
