package com.shokunin.taskmaster.src.service;

import com.shokunin.taskmaster.src.api.dto.MateriaRequestDTO;
import com.shokunin.taskmaster.src.api.mapper.MateriaMapper;
import com.shokunin.taskmaster.src.domain.Materia;
import com.shokunin.taskmaster.src.infrastructure.persistence.MateriaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MateriaService {
    private final MateriaRepository repository;
    private final MateriaMapper mapper;

    @Transactional
    public Materia save(MateriaRequestDTO dto){
        return repository.save(mapper.toEntity(dto));
    }

    public List<Materia> findAll() {
        return repository.findAll();
    }

    public Materia findById(Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Matéria não encontrada"));
     }
}
