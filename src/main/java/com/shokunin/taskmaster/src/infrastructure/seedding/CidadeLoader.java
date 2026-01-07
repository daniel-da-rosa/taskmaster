package com.shokunin.taskmaster.src.infrastructure.seedding;


import com.shokunin.taskmaster.src.domain.Cidade;
import com.shokunin.taskmaster.src.domain.UF;
import com.shokunin.taskmaster.src.infrastructure.persistence.CidadeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;

@Component
@Profile("!test")// impede de rodar durante os testes.
public class CidadeLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(CidadeLoader.class);
    private final CidadeRepository cidadeRepository;
    private final RestClient  restClient;

    public CidadeLoader(CidadeRepository cidadeRepository){
        this.cidadeRepository = cidadeRepository;
        this.restClient = RestClient.create("https://servicodados.ibge.gov.br");
    }

    @Override
    public void run(String... args){
        if (cidadeRepository.count() >1 ){
            log.info("Tabela de Cidades já populada, API não será consumida.");
            return;
        }

        log.info("Começando carga massiva de cidades para o banco de dados");
        Long start = System.currentTimeMillis();

        try{
            List<IbgeMunicipioResponse>municipios = restClient.get()
                    .uri("/api/v1/localidades/municipios")
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});
            if(municipios ==null || municipios.isEmpty()){
                log.warn("IBGE api returned no data!");
                return;

            }
            //mapeado a entidade
            List<Cidade> cityBatch = new ArrayList<>();
            for (IbgeMunicipioResponse m : municipios){
                try{
                    String sigla = m.microrregiao().mesorregiao().UF().sigla;
                    Cidade cidade = new Cidade();
                    cidade.setNome(m.nome());
                    cidade.setUf(UF.valueOf(sigla));

                    cityBatch.add(cidade);
                }catch (Exception e){
                    log.warn("Skipping city {}: Invalid data structure",m.nome());
                }
            }
            cidadeRepository.saveAll(cityBatch);

            Long duration = System.currentTimeMillis() - start;
            log.info("Finished! {} cities imported in {}ms.",cityBatch.size(),duration);
        }catch (Exception e){
            log.error("Critical failure loading cities form IBGE",e);
        }

    }
    record IbgeMunicipioResponse(String nome, IbgeMicro microrregiao) {}
    record IbgeMicro(IbgeMeso mesorregiao) {}
    record IbgeMeso(IbgeUF UF) {}
    record IbgeUF(String sigla) {}

}
