package com.shokunin.taskmaster.src.infrastructure.persistence;

import com.shokunin.taskmaster.src.domain.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TurmaRepository extends JpaRepository<Turma, Long> {

    Optional<Turma> findByInstituicaoId(Long instituicaoId);
    boolean existsByNomeAndInstituicaoId(String nome, Long instituicaoId);
}
