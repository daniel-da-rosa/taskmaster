package com.shokunin.taskmaster.src.infrastructure.persistence;

import com.shokunin.taskmaster.src.domain.Questao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface QuestaoRepository extends JpaRepository<Questao, Long> {

    List<Questao> findByMateriaId(Long materiaId);

    @Query("SELECT q FROM Questao q JOIN FETCH q.alternativas WHERE q.id = :id")
    Optional<Questao> findByIdWithAlternativas(@Param("id") Long id);

    boolean existsByEnunciadoAndMateriaId(String enunciado, Long materiaId);
    boolean existsByEnunciadoAndMateriaIdAndIdNot(String enunciado, Long materiaId, Long id);
}
