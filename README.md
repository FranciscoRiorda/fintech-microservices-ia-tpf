# FinTech Microservices - Proyecto Final

Ecosistema de microservicios bancarios basado en **Spring Cloud** con descubrimiento de servicios automático (Eureka), configuración centralizada (Config Server), y comunicación inter-servicios con Feign. Trabajo final de la Diplomatura en IA + Trabajo Práctico Finanzas.

## 🏗️ Arquitectura

```
┌──────────────────────────────────────────────────────────────────────┐
│                         Repositorio Git Remoto                       │
│           https://github.com/FranciscoRiorda/fintech-config-repo     │
└────────────────────────┬─────────────────────────────────────────────┘
                         │ (lee configuración)
                         ▼
            ┌────────────────────────┐
            │    Config Server       │
            │      (8888)            │
            └──────┬────────┬────────┘
                   │        │
         ┌─────────┘        └──────────┐
         │                             │
         ▼                             ▼
    ┌─────────────┐           ┌──────────────────┐
    │   Eureka    │           │ Customer-Service │
    │   Server    │           │     (8080)       │
    │   (8761)    │           │                  │
    └─────────────┘           │ • CRUD Clientes  │
         ▲                    │ • Feign Client   │
         │                    │ • Orquestación   │
         │                    └────────┬─────────┘
         │                             │
         │ (registro)                  │ (Feign + Eureka lookup)
         │                             │
         │                    ┌────────▼──────────┐
         │                    │ Product-Service   │
         │                    │    (8081)         │
         │                    │                   │
         └────────────────────┤ • CRUD Productos  │
         (registro)           │ • H2 en memoria   │
                              │ • 4 datos de      │
                              │   ejemplo         │
                              └───────────────────┘
```

### Componentes

| Servicio | Puerto | Descripción |
|----------|--------|-------------|
| **Config Server** | 8888 | Lee configuración de repositorio Git remoto: https://github.com/FranciscoRiorda/fintech-config-repo |
| **Eureka Server** | 8761 | Registro centralizado de servicios; ambos servicios se registran automáticamente |
| **Customer Service** | 8080 | Gestión de clientes bancarios; orquesta llamadas a Product-Service vía Feign |
| **Product Service** | 8081 | Gestión de productos bancarios (cuentas, tarjetas, préstamos, inversiones) |

### Comunicación Inter-Servicios

- **Customer-Service → Product-Service**: Mediante **OpenFeign** con **Eureka Discovery**
  - No hay URLs hardcodeadas
  - Feign consulta Eureka por el nombre "product-service"
  - Soporta automáticamente balanceo de carga si Product-Service se replica
  - Ejemplo: `obtenerPerfilCompleto(id)` combina datos del cliente + sus productos desde otro servicio

---

## 🚀 Requisitos Previos

- **Java 21** (JDK)
- **Maven 3.8+** (o usar el wrapper incluido: `./mvnw`)
- **Acceso a internet** (para que Config Server descargue la configuración del repositorio Git remoto)

---

## 🔴 Orden de Arranque (CRÍTICO)

Los servicios **deben iniciarse en este orden exacto**. Cada uno depende del anterior:

```
1️⃣  Eureka Server     → cd eureka-server && ./mvnw spring-boot:run
2️⃣  Config Server     → cd config-server && ./mvnw spring-boot:run
3️⃣  Product-Service   → cd product-service && ./mvnw spring-boot:run
4️⃣  Customer-Service  → cd customer-service && ./mvnw spring-boot:run
```

> ⚠️ **Por qué este orden:**
> - Eureka debe estar listo primero (es el service registry)
> - Config Server debe estar listo antes de que los servicios de negocio arranquen (necesitan su configuración)
> - Product-Service antes que Customer-Service (Customer-Service depende de Product-Service vía Feign)

### Ejecución Paso a Paso

Abre **4 terminales independientes** y ejecuta en cada una:

**Terminal 1 — Eureka Server:**
```bash
cd eureka-server
./mvnw spring-boot:run
```
✅ Listo cuando veas: `Tomcat started on port(s): 8761`

**Terminal 2 — Config Server:**
```bash
cd config-server
./mvnw spring-boot:run
```
✅ Listo cuando veas: `Tomcat started on port(s): 8888`

**Terminal 3 — Product-Service:**
```bash
cd product-service
./mvnw spring-boot:run
```
✅ Listo cuando veas: `Tomcat started on port(s): 8081` y el servicio registrado en Eureka

**Terminal 4 — Customer-Service:**
```bash
cd customer-service
./mvnw spring-boot:run
```
✅ Listo cuando veas: `Tomcat started on port(s): 8080` y el servicio registrado en Eureka

### Verificación

1. **Eureka Dashboard** (verifica que ambos servicios estén registrados):
   ```
   http://localhost:8761
   ```
   Deberías ver en "Instances currently registered with Eureka":
   - `PRODUCT-SERVICE`
   - `CUSTOMER-SERVICE`

2. **Config Server** (verifica que sirve configuración):
   ```
   http://localhost:8888/customer-service/default
   http://localhost:8888/product-service/default
   ```
   Deberías recibir JSON con la configuración de cada servicio

3. **Swagger UI** de cada servicio:
   - Product-Service: http://localhost:8081/swagger-ui.html
   - Customer-Service: http://localhost:8080/swagger-ui.html

---

## 📋 Endpoints Disponibles

### Product-Service (Puerto 8081)

| Método | Endpoint | Descripción | Ejemplo |
|--------|----------|-------------|---------|
| **GET** | `/api/products/cliente/{clienteId}` | Listar productos de un cliente | `/api/products/cliente/1` |
| **GET** | `/api/products/{id}` | Obtener un producto | `/api/products/1` |
| **POST** | `/api/products` | Crear nuevo producto | Body: `{tipo, clienteId, descripcion, monto}` |
| **PUT** | `/api/products/{id}` | Actualizar producto | Body: `{tipo, clienteId, descripcion, monto}` |
| **DELETE** | `/api/products/{id}` | Eliminar producto | `/api/products/1` |

**Swagger UI:** http://localhost:8081/swagger-ui.html

**Datos de ejemplo (precargados):**
- Cliente 1: 2 productos (CUENTA + TARJETA)
- Cliente 2: 2 productos (PRÉSTAMO + INVERSIÓN)

---

### Customer-Service (Puerto 8080)

| Método | Endpoint | Descripción | Ejemplo |
|--------|----------|-------------|---------|
| **GET** | `/api/customers` | Listar todos los clientes | — |
| **GET** | `/api/customers/{id}` | Obtener cliente por ID | `/api/customers/1` |
| **POST** | `/api/customers` | Crear nuevo cliente | Body: `{nombre, documento, correo, saldo}` |
| **PUT** | `/api/customers/{id}` | Actualizar cliente | Body: `{nombre, documento, correo, saldo}` |
| **GET** | `/api/customers/{id}/perfil` | ⭐ **Orquestación**: Cliente + sus productos | `/api/customers/1/perfil` |

**Swagger UI:** http://localhost:8080/swagger-ui.html

**Datos de ejemplo (precargados):**
- Cliente 1: Juan Pérez
- Cliente 2: María García

---

## 🎯 Ejemplo de Uso — Endpoint de Orquestación

El endpoint `GET /api/customers/{id}/perfil` es el punto estrella: combina datos del cliente + sus productos desde Product-Service vía Feign.

### Request

```bash
curl -X GET "http://localhost:8080/api/customers/1/perfil" \
  -H "Content-Type: application/json"
```

### Response (200 OK)

```json
{
  "id": 1,
  "nombre": "Juan Pérez",
  "documento": "12345678",
  "correo": "juan@example.com",
  "saldo": 10000.00,
  "productos": [
    {
      "id": 1,
      "clienteId": 1,
      "tipo": "CUENTA",
      "descripcion": "Cuenta Corriente Premium",
      "monto": 50000.00
    },
    {
      "id": 2,
      "clienteId": 1,
      "tipo": "TARJETA",
      "descripcion": "Tarjeta de Crédito Platino",
      "monto": 25000.00
    }
  ]
}
```

> 🔄 **¿Cómo funciona internamente?**
> 1. Customer-Service busca el cliente en su BD local
> 2. Customer-Service invoca `ProductServiceClient` (Feign)
> 3. Feign consulta Eureka por "product-service"
> 4. Feign realiza GET `/api/products/cliente/1` en Product-Service
> 5. Product-Service retorna la lista de productos
> 6. Customer-Service combina ambos y retorna el JSON unificado

---

## 🔧 Configuración Centralizada

Toda la configuración de los servicios (puertos, datasource, Eureka, JPA) **está centralizada en un repositorio Git remoto**:

📍 **Repositorio de Configuración:**
```
https://github.com/FranciscoRiorda/fintech-config-repo
```

**¿Qué archivos de configuración contiene?**
- `customer-service.yml` — Configuración de Customer-Service
- `product-service.yml` — Configuración de Product-Service

**¿Cómo funciona?**
1. Config Server (puerto 8888) se conecta al repositorio remoto
2. Customer-Service y Product-Service consultan Config Server al arrancar
3. Descargan dinámicamente su configuración específica
4. **Ventaja:** Puedes cambiar puertos, datasource, etc. sin tocar el código

**En `application.yml` local de cada servicio solo hay:**
```yaml
spring:
  application:
    name: customer-service  # o product-service
  config:
    import: "configserver:http://localhost:8888"
```

---

## 📂 Estructura del Proyecto

```
proyecto-final-fintech/
├── eureka-server/                    # Service Registry
│   ├── pom.xml
│   └── src/main/java/.../EurekaServerApplication.java
│
├── config-server/                    # Gestión centralizada de config
│   ├── pom.xml
│   └── src/main/java/.../ConfigServerApplication.java
│
├── product-service/                  # Microservicio de Productos
│   ├── pom.xml
│   └── src/main/java/com/fintech/productservice/
│       ├── controller/ProductController.java
│       ├── service/ProductService.java
│       ├── repository/ProductRepository.java
│       ├── model/Product.java
│       ├── dto/ProductDTO.java
│       └── config/OpenApiConfig.java
│
├── customer-service/                 # Microservicio de Clientes
│   ├── pom.xml
│   └── src/main/java/com/fintech/customerservice/
│       ├── controller/CustomerController.java
│       ├── service/CustomerService.java
│       ├── repository/CustomerRepository.java
│       ├── client/ProductServiceClient.java    # Feign Client
│       ├── model/Customer.java
│       ├── dto/
│       │   ├── CustomerDTO.java
│       │   ├── CustomerPerfilDTO.java
│       │   └── ProductDTO.java
│       ├── exception/
│       │   ├── ClienteNoEncontradoException.java
│       │   └── GlobalExceptionHandler.java
│       ├── config/
│       │   ├── OpenApiConfig.java
│       │   └── DataSeeding.java
│       └── CustomerServiceApplication.java     # @EnableFeignClients
│
├── README.md                         # Este archivo
├── DEVELOPMENT_PLAN.md               # Plan de desarrollo con checklist
└── .gitignore
```

---

## 📦 Stack Tecnológico

| Componente | Versión | Uso |
|-----------|---------|-----|
| **Java** | 21 | Lenguaje |
| **Spring Boot** | 3.5.x | Framework base |
| **Spring Cloud** | 2025.0.0 (BOM) | Orquestación de microservicios |
| **Eureka** | Incluido en Spring Cloud | Service Discovery / Registry |
| **Config Server** | Incluido en Spring Cloud | Configuración centralizada |
| **OpenFeign** | Incluido en Spring Cloud | Comunicación inter-servicios |
| **JPA/Hibernate** | Incluido en Spring Boot | ORM |
| **H2 Database** | En memoria | Base de datos local de cada servicio |
| **Lombok** | Latest | Boilerplate reduction (@Data, @Entity, etc.) |
| **SpringDoc OpenAPI** | 2.6.0 | Swagger UI y OpenAPI docs |
| **Maven** | 3.8+ | Build tool |

---

## 🧪 Testeo Rápido

Una vez que todos los servicios estén arrancados, puedes probar:

### 1. Verificar Eureka (Service Discovery)
```bash
curl http://localhost:8761
# Deberías ver el dashboard HTML de Eureka
```

### 2. Verificar Config Server
```bash
curl http://localhost:8888/customer-service/default
# Deberías recibir JSON con la configuración
```

### 3. Listar todos los clientes
```bash
curl http://localhost:8080/api/customers
```

### 4. Obtener perfil completo de cliente (ORQUESTACIÓN)
```bash
curl http://localhost:8080/api/customers/1/perfil
# Retorna cliente + sus productos desde Product-Service
```

### 5. Usar Swagger UI (interfaz gráfica)
- **Customer-Service:** http://localhost:8080/swagger-ui.html
- **Product-Service:** http://localhost:8081/swagger-ui.html

---

## 🛠️ Solución de Problemas

### ❌ "Connection refused" en cliente
- Verifica que Eureka esté arrancado (puerto 8761)
- Verifica que Config Server esté arrancado (puerto 8888)

### ❌ "Config server not responding"
- Verifica que el repositorio Git remoto sea accesible
- Verifica la URL en `config-server/application.yml`

### ❌ "Eureka says no instances"
- Espera 30 segundos: Eureka necesita heartbeat de los servicios
- Verifica que los servicios tengan `spring.application.name` correcto

### ❌ "404 en /api/customers/1/perfil"
- Verifica que Product-Service esté registrado en Eureka
- Verifica que exista cliente con ID 1 en Customer-Service

---

## 📚 Documentación Adicional

- **DEVELOPMENT_PLAN.md** — Plan de desarrollo, tareas completadas y pendientes
- **Swagger UI** — Documentación interactiva de endpoints (ver sección Testeo Rápido)

---

**Última actualización:** Julio 2026
