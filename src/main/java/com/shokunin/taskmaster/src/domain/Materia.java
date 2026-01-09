package com.shokunin.taskmaster.src.domain;

import com.shokunin.taskmaster.src.domain.enums.NivelEnsino;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Table(name="materia", uniqueConstraints = {
        @UniqueConstraint(name = "uk_materia_nivel", columnNames = {"nome", "nivel_ensino"})
})
public class Materia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private long id;

    @Column(nullable = false)
    private String nome;

    @Column(length = 500)
    private String ementa;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_ensino", nullable = false)
    private NivelEnsino nivelEnsino;
}
