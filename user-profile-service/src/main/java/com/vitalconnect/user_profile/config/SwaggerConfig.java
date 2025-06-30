package com.vitalconnect.user_profile.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API VitalConnect - Gestión de Perfiles de Usuario")
                        .version("1.1")
                        .description("Microservicio que permite crear, actualizar, eliminar y consultar perfiles de usuario."))
                .servers(List.of(
                        new Server().url("http://localhost:8081").description("Servidor local")
                ))
                .externalDocs(new ExternalDocumentation()
                        .description("Documentación del proyecto")
                        .url("https://github.com/milegf/vitalconnect-user-profile/tree/main/user-profile-service"));    }

}
