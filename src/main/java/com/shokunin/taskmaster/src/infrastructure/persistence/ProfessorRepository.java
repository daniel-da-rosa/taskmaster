package com.shokunin.taskmaster.src.infrastructure.persistence;

import com.shokunin.taskmaster.src.domain.Professor;
import com.shokunin.taskmaster.src.domain.types.Cpf;
import com.shokunin.taskmaster.src.domain.types.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    boolean existsByCpf(Cpf cpf); //O Spring Data entende o ValueObject se o Converter estiver ok
    boolean existsByEmail(Email email);

    Optional<Professor> findByCpf(Cpf cpf);
    Optional<Professor> findByEmail(Email email);
    boolean existsByCpfAndIdNot(Cpf cpf, Long id);
    boolean existsByEmailAndIdNot(Email email, Long id);
}
