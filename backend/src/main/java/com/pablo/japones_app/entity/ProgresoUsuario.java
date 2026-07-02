package com.pablo.japones_app.entity;

import com.pablo.japones_app.enums.EstadoProgreso;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "progreso_usuario", uniqueConstraints = {
        @UniqueConstraint(name = "uq_usuario_flashcard", columnNames = {"usuario_id", "flashcard_id"})
})
public class ProgresoUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flashcard_id", nullable = false)
    private FlashCard flashCard;

    @Column(name = "factor_facilidad", nullable = false)
    private BigDecimal factorFacilidad = new BigDecimal("2.5");

    @Column(name = "intervalo", nullable = false)
    private Integer intervalo = 0;

    @Column(name = "repeticiones", nullable = false)
    private Integer repeticiones = 0;

    @Column(name = "proxima_revision", nullable = false)
    private LocalDate proximaRevision = LocalDate.now();

    @Column(name = "ultima_revision" )
    private LocalDate ultimaRevision;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "estado" , nullable = false)
    private EstadoProgreso estadoProgreso;
}
