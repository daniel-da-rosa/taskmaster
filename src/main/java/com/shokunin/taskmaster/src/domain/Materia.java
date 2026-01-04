package com.shokunin.taskmaster.src.domain;

import com.shokunin.taskmaster.src.domain.enums.NivelEnsino;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

@Table(name="materia",uniqueConstraints = {
        @UniqueConstraint(name = "uk_materia_nivel",columnNames = {"nome","nivel"})
})
public class Materia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String nome;

    @Column(length = 500)
    private String ementa;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NivelEnsino nivelEnsino;
}
