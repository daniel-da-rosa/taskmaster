package com.shokunin.taskmaster.src.infrastructure.persistence;

import com.shokunin.taskmaster.src.domain.Instituicao;
import com.shokunin.taskmaster.src.domain.types.Cnpj;
import com.shokunin.taskmaster.src.domain.types.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InstituicaoRepository extends JpaRepository<Instituicao, Long> {
    Optional<Instituicao> findByCnpj(Cnpj cnpj);
    Optional<Instituicao> findByEmail(Email email);
    List<Instituicao> findByProfessorId(Long professorId);
    boolean existsByCnpj(Cnpj cnpj);
    boolean existsByCnpjAndIdNot(Cnpj cnpj, Long id);
}
