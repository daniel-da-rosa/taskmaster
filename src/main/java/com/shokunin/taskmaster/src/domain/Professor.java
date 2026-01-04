package com.shokunin.taskmaster.src.domain;

import com.shokunin.taskmaster.src.domain.types.Cpf;
import com.shokunin.taskmaster.src.domain.types.Email;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter

@ToString(exclude = "instituicoes")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@NoArgsConstructor
@AllArgsConstructor
public class Professor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private Cpf cpf;

    @Column(nullable = false)
    private Email email;

    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL,orphanRemoval = true)
    List<Instituicao>instituicoes = new ArrayList<>();

    public void adicionarInstituicoes(Instituicao instituicao){
        instituicoes.add(instituicao);
        instituicao.setProfessor(this);
    }


}
