package com.shokunin.taskmaster.src.config;

import com.shokunin.taskmaster.src.domain.Cidade;
import com.shokunin.taskmaster.src.domain.UF;
import com.shokunin.taskmaster.src.repository.CidadeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CargaInicial {

    @Bean
    public CommandLineRunner dataSeeder(CidadeRepository cidadeRepository){
        return args -> {
            if(cidadeRepository.count() == 0){
                Cidade cidade = new Cidade();
                cidade.setNome("São Paulo");
                cidade.setUf(UF.SP);
                cidadeRepository.save(cidade);
                System.out.println("⚡ MenteMestra: Cidade 'São Paulo' (ID 1) criada com sucesso!");

            }

        };
    }
}
