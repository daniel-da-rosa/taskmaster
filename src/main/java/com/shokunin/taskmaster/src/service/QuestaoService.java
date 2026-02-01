package com.shokunin.taskmaster.src.service;

import com.shokunin.taskmaster.src.api.dto.AlternativaRequestDTO;
import com.shokunin.taskmaster.src.api.dto.QuestaoRequestDTO;
import com.shokunin.taskmaster.src.api.dto.response.QuestaoResponseDTO;
import com.shokunin.taskmaster.src.api.mapper.QuestaoMapper;
import com.shokunin.taskmaster.src.domain.Alternativa;
import com.shokunin.taskmaster.src.domain.Materia;
import com.shokunin.taskmaster.src.domain.Questao;
import com.shokunin.taskmaster.src.infrastructure.exception.RegraDeNegocioException;
import com.shokunin.taskmaster.src.infrastructure.persistence.MateriaRepository;
import com.shokunin.taskmaster.src.infrastructure.persistence.QuestaoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestaoService {
    private final QuestaoRepository repository;
    private final MateriaRepository materiaRepository;
    private final QuestaoMapper mapper;

    @Transactional
    public QuestaoResponseDTO save(Long materiaId , QuestaoRequestDTO dto){

        if(repository.existsByEnunciadoAndMateriaId(dto.enunciado(), materiaId)){
            throw new RegraDeNegocioException("Já existe uma questão com o enunciado "+dto.enunciado());
        }
        //Validação da Regra do Highlander (Apenas uma correta)
        validarAlternativas(dto.alternativas());

        Materia materia = materiaRepository.findById(materiaId)
                .orElseThrow(() -> new RegraDeNegocioException("Materia não encontrada"));

        var questao = mapper.toEntity(dto);
        questao.setMateria(materia);
        questao.getAlternativas().forEach(a-> a.setQuestao(questao));

        var questaoSalva = repository.save(questao);
        return mapper.toDTO(questaoSalva);

    }

    public List<QuestaoResponseDTO> listaPorMateria(Long materiaId){
        if(!materiaRepository.existsById(materiaId)){
            throw new EntityNotFoundException("Matéria não encontrada!");
        }
        return repository.findByMateriaId(materiaId).stream()
                .map(mapper::toDTO)
                .toList();
    }

    public QuestaoResponseDTO buscarPorId(Long id){
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(()->new EntityNotFoundException("Questão não encontrada!"));

    }

    //--Update
    @Transactional
    public QuestaoResponseDTO atualizar(Long id, QuestaoRequestDTO dto){
        var questao = repository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Questão não encontrada!"));

        validarAlternativas(dto.alternativas());

        if(repository.existsByEnunciadoAndMateriaIdAndIdNot(dto.enunciado(),questao.getMateria().getId(),id)){
            throw new RegraDeNegocioException("Já existe outra questão com esse enuciado!");
        }
        mapper.updateEntityFromDto(dto,questao);
        questao.getAlternativas().stream()
                .forEach( q-> q.setQuestao(questao));
        return mapper.toDTO(repository.save(questao));
    }

    //--Delete
    public QuestaoResponseDTO excluir(Long id){
        var questao = repository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Questão não encontrada!"));

        var dto = mapper.toDTO(questao);

        try{
            repository.delete(questao);
            repository.flush();
        }catch (DataIntegrityViolationException e){
            throw  new RegraDeNegocioException("Não é possível excluir a questão pois ela já foi respondida ou está em uso.");
        }
        return dto;
    }

    //--Validação
    public void validarAlternativas(List<AlternativaRequestDTO> alternativas){
        long alternativasCorretas = alternativas.stream()
                .filter(alternativa -> alternativa.correta())
                .count();
        if(alternativasCorretas != 1){
            throw new RegraDeNegocioException("Deve haver exatamente uma alternativa correta");
        }

    }


}
