package com.pablo.japones_app.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro;

    @Column(name = "puntos_totales", nullable = false)
    private Integer puntosTotales = 0;

    @Column(name = "racha_actual", nullable = false)
    private int rachaActual = 0;

    @Column(name = "racha_maxima", nullable = false)
    private int rachaMaxima = 0;

    @Column(name = "ultimo_dia_activo")
    private LocalDate ultimoDiaActivo;

    //Relacion inversa con CuentaAutenticacion
    @ToString.Exclude
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CuentaAutenticacion> cuentasAutenticacion;

    @OneToMany(mappedBy = "creador")
    private List<FlashCard> flashcardsCreadas = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProgresoUsuario> progresos = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Suscripcion> suscripciones = new ArrayList<>();
}
