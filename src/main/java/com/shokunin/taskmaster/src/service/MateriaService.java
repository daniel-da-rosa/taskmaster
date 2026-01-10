package com.shokunin.taskmaster.src.service;

import com.shokunin.taskmaster.src.api.dto.MateriaRequestDTO;
import com.shokunin.taskmaster.src.api.dto.response.MateriaResponseDTO;
import com.shokunin.taskmaster.src.api.mapper.MateriaMapper;
import com.shokunin.taskmaster.src.domain.Materia;
import com.shokunin.taskmaster.src.infrastructure.exception.RegraDeNegocioException;
import com.shokunin.taskmaster.src.infrastructure.persistence.MateriaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MateriaService {
    private final MateriaRepository repository;
    private final MateriaMapper mapper;

    @Transactional
    public Materia save(MateriaRequestDTO dto){

        if(repository.existsByNomeAndNivelEnsino(dto.nome(),dto.nivelEnsino())){
            throw new RegraDeNegocioException("Já existe uma matéria com essa descrição para esse nivel de ensino");
        }
        return repository.save(mapper.toEntity(dto));
    }

    public Page<MateriaResponseDTO> listar(Pageable pageable) {

        return repository.findAll(pageable)
                .map(mapper::toDTO);
    }

    public Materia findById(Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Matéria não encontrada"));
     }

     @Transactional
     public MateriaResponseDTO atualizar(Long id, MateriaRequestDTO dto){

        var materia = repository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Matéria não encontrada"));

        if(repository.existsByNomeAndNivelEnsino(dto.nome(),dto.nivelEnsino())){
            throw  new RegraDeNegocioException("Já existe outra matéria com este nome e nível de ensino. ");
        }

        mapper.updateEntityFromDto(dto,materia);

        return mapper.toDTO(repository.save(materia));

     }

     @Transactional
     public MateriaResponseDTO excluir(Long id){
        var entity = repository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Materia não encontrada!"));

        var responseDTO = mapper.toDTO(entity);

        try {
            repository.delete(entity);
            repository.flush(); //força o hibernate e executar o Delete agora
        }catch (DataIntegrityViolationException e){
            throw new RegraDeNegocioException("Não é possivel exlcuir a Materia '%s' pois ela possui vínculos (ex: tarefas ou provas).".formatted(entity.getNome()));
        }
        return responseDTO;
     }


}
