package com.shokunin.taskmaster.src.infrastructure.persistence;

import com.shokunin.taskmaster.src.domain.Materia;
import com.shokunin.taskmaster.src.domain.enums.NivelEnsino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MateriaRepository extends JpaRepository<Materia, Long> {

    boolean existsByNomeAndNivelEnsino(String nome, NivelEnsino nivelEnsino);

    List<Materia> findByNivelEnsino(NivelEnsino nivelEnsino);
}
