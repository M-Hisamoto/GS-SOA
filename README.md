# SpaceOps — Plataforma de Monitoramento de Sensores em Ambientes Extremos

API REST para monitoramento de telemetria de sensores instalados em ambientes extremos.
Aplicável a contextos hostis: ESPACIAIS E TERRESTRES

---

## Arquitetura

Arquitetura em camadas com separação clara de responsabilidades:

**Responsabilidade de cada camada:**

- **Controller** — expõe os endpoints REST, valida entrada (`@Valid`), traduz HTTP. Não contém regra de negócio.
- **Service** — concentra as regras de negócio e o controle transacional.
- **Repository** — acesso a dados via Spring Data JPA.
- **Model/Entity** — entidades JPA com regras de domínio próprias.
- **DTO** — contratos de entrada (`request`) e saída (`response`), isolando as entidades da API.

---

## Stack utilizada

| Categoria | Tecnologia |
|---|---|
| Linguagem | Java 21 (LTS) |
| Framework | Spring Boot 3.3.5 |
| Web | Spring Web (REST) |
| Persistência | Spring Data JPA + Hibernate |
| Banco de dados | Oracle (H2 apenas para o desenvolvimento) |
| Segurança | Spring Security + JWT (jjwt 0.12.6) |
| Documentação | springdoc-openapi (Swagger UI) |
| Validação | Jakarta Bean Validation |
| Build | Maven |
| Utilitários | Lombok |

---

## Como executar

**Pré-requisitos:** JDK 21 e Maven (ou use o wrapper `./mvnw`).

```bash
# 1. Clonar o repositório
git clone https://github.com/M-Hisamoto/GS-SOA.git
cd spaceops

# 2. Executar
mvn spring-boot:run
```

A aplicação sobe em `http://localhost:8080`.

| Recurso | URL |
|---|---|
| Swagger UI | http://localhost:8080/swagger-ui.html |
| OpenAPI JSON | http://localhost:8080/v3/api-docs |
| Console H2 | http://localhost:8080/h2-console |

**Conexão do console H2:** JDBC URL `jdbc:h2:mem:spaceops`, usuário `sa`, sem senha.

---

## Endpoints da API

### Autenticação (público)
| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/auth/register` | Registra usuário e retorna token |
| POST | `/auth/login` | Autentica e retorna token |

### Estações
| Método | Endpoint | Descrição | Status |
|---|---|---|---|
| GET | `/estacoes?ambiente=ESPACIAL&page=0&size=10` | Lista paginada (filtro opcional) | 200 |
| GET | `/estacoes/{id}` | Busca por id | 200 / 404 |
| POST | `/estacoes` | Cria estação | 201 |
| PUT | `/estacoes/{id}` | Atualiza | 200 / 404 |
| DELETE | `/estacoes/{id}` | Remove (ADMIN) | 204 / 403 |

### Sensores
| Método | Endpoint | Descrição | Status |
|---|---|---|---|
| GET | `/sensores?estacaoId=1&tipo=TEMPERATURA` | Lista paginada (filtros) | 200 |
| GET | `/sensores/{id}` | Busca por id | 200 / 404 |
| POST | `/sensores` | Cria sensor | 201 |
| PUT | `/sensores/{id}` | Atualiza | 200 / 404 |
| DELETE | `/sensores/{id}` | Remove (ADMIN) | 204 / 403 |

### Leituras
| Método | Endpoint | Descrição | Status |
|---|---|---|---|
| POST | `/leituras` | Registra leitura (gera alerta se violar threshold) | 201 |
| GET | `/leituras/{id}` | Busca por id | 200 / 404 |
| GET | `/leituras?sensorId=1&page=0` | Lista leituras de um sensor | 200 |
| GET | `/leituras/periodo?sensorId=1&inicio=2026-05-26T00:00:00&fim=2026-05-27T00:00:00` | Por período | 200 |
| GET | `/sensores/{sensorId}/leituras/recentes?limite=10` | Últimas N leituras | 200 |

### Alertas
| Método | Endpoint | Descrição | Status |
|---|---|---|---|
| GET | `/alertas?status=ABERTO&page=0` | Lista paginada (filtro) | 200 |
| GET | `/alertas/{id}` | Busca por id | 200 / 404 |
| PATCH | `/alertas/{id}/status?novoStatus=EM_ANALISE` | Atualiza status | 200 / 400 |
| PATCH | `/alertas/{id}/resolver` | Marca como RESOLVIDO | 200 / 400 |

## Integrantes

| Nome | RM |
|---|---|
| _Guilherme_ | _554962_ |
| _Pedro_ | _555556_ |
| _Fabrício_ | _558216_ |
| _Vitor_ | _554893_ |
| _Matheus_ | _555447_ |