package com.shokunin.taskmaster.src.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI OpenApiConfig(){
        return new OpenAPI()
                .info(new Info()
                        .title("TaskMaster API")
                        .description("API para gerenciamento de matérias e tarefas escolares")
                        .contact(new Contact()
                                .name("Shokunin")
                                .email("mestre@shokunin.group"))
                        .version("1.0.0"));

    }
}
