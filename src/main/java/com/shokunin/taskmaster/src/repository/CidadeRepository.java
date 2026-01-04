package com.shokunin.taskmaster.src.repository;

import com.shokunin.taskmaster.src.domain.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long> {}
