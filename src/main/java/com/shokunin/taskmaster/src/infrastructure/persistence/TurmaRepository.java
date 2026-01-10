package com.shokunin.taskmaster.src.infrastructure.persistence;

import com.shokunin.taskmaster.src.domain.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TurmaRepository extends JpaRepository<Turma, Long> {

    List<Turma> findByInstituicaoId(Long instituicaoId);
    boolean existsByNomeAndInstituicaoId(String nome, Long instituicaoId);
    // Validação para o UPDATE: verifica se o nome já existe na mesma escola, mas IGNORA a própria turma que está sendo editada.
    boolean existsByNomeAndInstituicaoIdAndIdNot(String nome, Long instituicaoId, Long id);
}
