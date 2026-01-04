package com.shokunin.taskmaster.src.domain;


import com.shokunin.taskmaster.src.domain.types.Cnpj;
import com.shokunin.taskmaster.src.domain.types.CnpjConverter;
import com.shokunin.taskmaster.src.domain.types.ConverteEmail;
import com.shokunin.taskmaster.src.domain.types.Email;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Instituicao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //atributos
    @Column(nullable = false)
    private String nome;

    @Column(unique = true, length = 18)
    @Convert(converter = CnpjConverter.class)
    private Cnpj cnpj;

    @Column(length = 100)
    @Convert(converter = ConverteEmail.class)
    private Email email;

    private String telefone;
    private String endereco;
    //relacionamentos
    @ManyToOne
    @JoinColumn(name = "cidade_id")
    private Cidade cidade;

    @ManyToOne
    @JoinColumn(name="professor")
    private Professor professor;
}
