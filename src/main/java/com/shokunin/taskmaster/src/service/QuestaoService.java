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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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

        Questao questao = new Questao();
        questao.setEnunciado(dto.enunciado());
        questao.setMateria(materia);

        dto.alternativas().forEach(altDto ->{
            Alternativa alternativa = new Alternativa();
            alternativa.setTexto(altDto.texto());
            alternativa.setCorreta(altDto.correta());
            questao.addAlternativa(alternativa);

        });
        repository.save(questao);
        return mapper.toDTO(questao);



    }
    public void validarAlternativas(List<AlternativaRequestDTO> alternativas){
        long alternativasCorretas = alternativas.stream()
                .filter(alternativa -> alternativa.correta())
                .count();
        if(alternativasCorretas != 1){
            throw new RegraDeNegocioException("Deve haver exatamente uma alternativa correta");
        }

    }

    public List<QuestaoResponseDTO> listaPorMateria(Long materiaId){
        return repository.findByMateriaId(materiaId).stream()
                .map(mapper::toDTO)
                .toList();

    }

}
