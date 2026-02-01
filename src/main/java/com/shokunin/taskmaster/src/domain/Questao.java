package com.shokunin.taskmaster.src.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"alternativas","materia"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Questao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank(message = "O enunciado da questão é obrigatório")
    @Column(columnDefinition = "TEXT", nullable = false)
    private String enunciado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "materia_id", nullable = false)
    private Materia materia;

    @OneToMany(mappedBy = "questao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Alternativa> alternativas = new ArrayList<>();

    public void addAlternativa(Alternativa alternativa){
        this.alternativas.add(alternativa);
        alternativa.setQuestao(this);
    }


}
