package com.fiap.spaceops.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    private static final String SECURITY_SCHEME = "bearer-jwt";

    @Bean
    public OpenAPI spaceOpsOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("SpaceOps API")
                        .description("Plataforma de Monitoramento de Sensores em Ambientes Extremos. "
                                + "Solucao alinhada ao ODS 9 - Industria, Inovacao e Infraestrutura. "
                                + "FIAP Global Solution - Space Connect.")
                        .version("1.0.0")
                        .contact(new Contact().name("Equipe SpaceOps - FIAP 3ESPY"))
                        .license(new License().name("Uso academico")))
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME))
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME, new SecurityScheme()
                                .name(SECURITY_SCHEME)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("Cole o token JWT obtido em /auth/login (sem o prefixo 'Bearer ').")));
    }
}
