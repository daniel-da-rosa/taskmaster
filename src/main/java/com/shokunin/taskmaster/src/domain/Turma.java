package com.shokunin.taskmaster.src.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "instituicao")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Turma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private  Long id;

    @NotBlank(message = "O nome da turma é obrigatório")
    @Column(nullable = false,length = 100)
    private String nome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instituicao_id",nullable = false)
    private Instituicao instituicao;



}
