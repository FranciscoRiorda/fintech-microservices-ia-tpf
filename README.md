# FinTech Microservices - Proyecto Final (Diplomatura IA + TP Finanzas)

## 📋 Descripción General

Arquitectura de microservicios para una plataforma FinTech utilizando **Spring Cloud**, **Eureka** y **Spring Cloud Config**. Sistema modular con gestión centralizada de configuraciones, descubrimiento de servicios y comunicación inter-servicios mediante Feign.

## 🏗️ Arquitectura

```
┌─────────────────────────────────────────────────────────────────┐
│                       Cliente / Frontend                         │
└──────────────────────┬──────────────────────────────────────────┘
                       │
    ┌──────────────────┼──────────────────┐
    │                  │                  │
    ▼                  ▼                  ▼
┌─────────────┐  ┌─────────────┐  ┌─────────────┐
│   Config    │  │   Eureka    │  │  Customer   │
│   Server    │  │   Server    │  │  Service    │
│  (8888)     │  │  (8761)     │  │  (8080)     │
└─────────────┘  └─────────────┘  └──────┬──────┘
                                          │
                                          │ Feign (Eureka lookup)
                                          │
                                          ▼
                                   ┌─────────────┐
                                   │   Product   │
                                   │  Service    │
                                   │  (8081)     │
                                   └─────────────┘
```

### Componentes

| Servicio | Puerto | Descripción |
|----------|--------|-------------|
| **Config Server** | 8888 | Gestión centralizada de configuraciones desde repositorio Git remoto |
| **Eureka Server** | 8761 | Registro y descubrimiento de servicios |
| **Customer Service** | 8080 | Gestión de clientes |
| **Product Service** | 8081 | Gestión de productos |

---

## 🚀 Instrucciones de Ejecución

### 1. **Orden de Arranque (IMPORTANTE)**

Los servicios deben iniciarse en este **orden específico**:

```
1. Config Server     → http://localhost:8888
2. Eureka Server     → http://localhost:8761
3. Product Service   → http://localhost:8081
4. Customer Service  → http://localhost:8080
```

### 2. **Ejecución en cada servicio**

Abre una terminal independiente para cada servicio y ejecuta:

```bash
# Desde la carpeta del servicio
cd config-server
./mvnw spring-boot:run

# En otra terminal
cd eureka-server
./mvnw spring-boot:run

# En otra terminal
cd product-service
./mvnw spring-boot:run

# En otra terminal
cd customer-service
./mvnw spring-boot:run
```

### 3. **Verificación**

- **Eureka Dashboard**: http://localhost:8761 (verifica que customer-service y product-service aparezcan registrados)
- **H2 Console Product**: http://localhost:8081/h2-console
- **H2 Console Customer**: http://localhost:8080/h2-console

---

## 📦 Tecnologías

- **Java**: 21
- **Spring Boot**: 3.5.0
- **Spring Cloud**: 2025.0.0 (BOM)
- **Spring Cloud Config**: Gestión centralizada de configuraciones
- **Eureka**: Service Discovery
- **OpenFeign**: Client HTTP declarativo
- **JPA/Hibernate**: ORM
- **H2**: Base de datos en memoria
- **Lombok**: Generación de código boilerplate
- **Maven**: Build tool

---

## 📂 Estructura del Proyecto

```
proyecto-final-fintech/
├── config-server/
│   ├── pom.xml
│   └── src/
│       └── main/
│           ├── java/com/fintech/configserver/
│           └── resources/application.yml
├── eureka-server/
│   ├── pom.xml
│   └── src/
│       └── main/
│           ├── java/com/fintech/eurekaserver/
│           └── resources/application.yml
├── customer-service/
│   ├── pom.xml
│   └── src/
│       └── main/
│           ├── java/com/fintech/customerservice/
│           │   ├── controller/
│           │   ├── service/
│           │   ├── repository/
│           │   ├── model/
│           │   ├── dto/
│           │   └── client/
│           └── resources/application.yml
├── product-service/
│   ├── pom.xml
│   └── src/
│       └── main/
│           ├── java/com/fintech/productservice/
│           │   ├── controller/
│           │   ├── service/
│           │   ├── repository/
│           │   ├── model/
│           │   └── dto/
│           └── resources/application.yml
├── README.md
└── DEVELOPMENT_PLAN.md
```

---

## 🔧 Configuración

### Config Server

El Config Server apunta a un repositorio Git remoto para las configuraciones. Reemplaza la URL placeholder en `config-server/src/main/resources/application.yml`:

```yaml
spring:
  cloud:
    config:
      server:
        git:
          uri: https://github.com/YOUR_USERNAME/fintech-config-repo.git
          default-label: main
          search-paths: configs
```

### Comunicación Inter-Servicios

**Customer Service** se comunica con **Product Service** mediante **OpenFeign**:

```java
@FeignClient(name = "product-service")
public interface ProductServiceClient {
    @GetMapping("/api/products/{id}")
    ProductDTO getProductById(@PathVariable Long id);
}
```

Feign utiliza Eureka automáticamente para resolver la URL de `product-service`.

---

## 📝 Próximos Pasos

Consulta `DEVELOPMENT_PLAN.md` para el plan detallado de desarrollo y las tareas pendientes.
