# DEVELOPMENT_PLAN.md - Plan de Desarrollo

## 🎯 Objetivo General

Desarrollar una arquitectura de microservicios FinTech funcional que integre Spring Cloud Config, Eureka Service Discovery, y servicios independientes de clientes y productos con comunicación inter-servicios mediante OpenFeign.

---

## ✅ Checklist de Fases

### **Fase 1: Scaffolding Inicial** ✅ COMPLETADO
- [x] Crear estructura de monorepo con proyectos Maven independientes
- [x] Configurar Config Server (puerto 8888) con @EnableConfigServer
- [x] Configurar Eureka Server (puerto 8761) con @EnableEurekaServer
- [x] Crear Product Service (puerto 8081) con capas (controller, service, repository, model, dto)
- [x] Crear Customer Service (puerto 8080) con capas + Feign client
- [x] Configurar application.yml para todos los servicios
- [x] Integrar Spring Cloud Config (spring.config.import) en servicios
- [x] Registrar servicios en Eureka con nombres exactos
- [x] Crear Product y Customer entities con campos básicos
- [x] Crear ProductDTO y CustomerDTO
- [x] Crear interfaz ProductServiceClient (sin implementar)
- [x] Crear README.md con instrucciones de ejecución
- [x] Crear DEVELOPMENT_PLAN.md

### **Fase 2: Lógica de Negocio - Product Service** ⏳ PENDIENTE
- [ ] Implementar ProductService (crear, actualizar, obtener, eliminar, listar)
- [ ] Implementar ProductRepository con custom queries si es necesario
- [ ] Implementar ProductController (GET /api/products, GET /api/products/{id}, POST, PUT, DELETE)
- [ ] Validaciones básicas en ProductService
- [ ] Pruebas unitarias para ProductService
- [ ] Pruebas de integración para ProductController

### **Fase 3: Lógica de Negocio - Customer Service** ⏳ PENDIENTE
- [ ] Implementar CustomerService (crear, actualizar, obtener, deletear, listar)
- [ ] Implementar CustomerRepository con custom queries
- [ ] Implementar CustomerController (GET /api/customers, GET /api/customers/{id}, POST, PUT, DELETE)
- [ ] Implementar métodos en ProductServiceClient (getFeignClient para obtener productos)
- [ ] Validaciones básicas en CustomerService
- [ ] Pruebas unitarias para CustomerService
- [ ] Pruebas de integración para CustomerController

### **Fase 4: Integración Feign** ⏳ PENDIENTE
- [ ] Implementar llamadas desde Customer Service a Product Service mediante Feign
- [ ] Verificar descubrimiento automático vía Eureka
- [ ] Manejo de errores y fallbacks (optional)
- [ ] Pruebas end-to-end de comunicación inter-servicios

### **Fase 5: Config Server con Repo Remoto Real** ⏳ PENDIENTE
- [ ] Crear repositorio remoto (GitHub, GitLab, etc.) con configuraciones externas
- [ ] Actualizar application.yml del Config Server con URL real
- [ ] Crear archivos de configuración para cada servicio en el repo remoto:
  - [ ] application.yml (general)
  - [ ] application-dev.yml
  - [ ] product-service.yml
  - [ ] customer-service.yml
- [ ] Validar que los servicios traigan config desde Config Server
- [ ] Pruebas de actualización de configuraciones dinámicas

### **Fase 6: Desafío Opcional - IA/Análisis de Datos** ⏳ PENDIENTE
- [ ] Evaluar requisito de análisis/IA (si aplica a diplomatura)
- [ ] Implementar endpoint de análisis en algún servicio (e.g., análisis de saldos, tendencias)
- [ ] Integrar con bibliotecas ML si es necesario (TensorFlow, sklearn via Python, etc.)
- [ ] Documentar desafío IA

### **Fase 7: Documentación Final** ⏳ PENDIENTE
- [ ] Documentar APIs (Swagger/OpenAPI en cada servicio)
- [ ] Crear guía de deployment (cómo empaquetar y subir a producción)
- [ ] Crear guía de troubleshooting
- [ ] Documentar decisiones arquitectónicas
- [ ] Actualizar README.md con info de documentación

---

## 📊 Estado Actual

### ✅ Completado en Esta Sesión

El proyecto se encuentra en **Fase 1: Scaffolding Inicial - 100% completado**.

Se han creado:

1. **Config Server** (`config-server/`)
   - Proyecto Maven independiente
   - @EnableConfigServer funcional
   - Puerto 8888
   - application.yml con placeholder para Git repository
   - Listo para apuntar a un repositorio remoto real

2. **Eureka Server** (`eureka-server/`)
   - Proyecto Maven independiente
   - @EnableEurekaServer funcional
   - Puerto 8761
   - Configuración standalone (no registrado en sí mismo)

3. **Product Service** (`product-service/`)
   - Estructura de capas completa (controller, service, repository, model, dto)
   - Entity `Product` con campos: id, tipo, clienteId, saldo
   - ProductDTO correspondiente
   - ProductRepository (JpaRepository)
   - ProductService (esqueleto con TODO)
   - ProductController (esqueleto con TODO)
   - Integración con Eureka (EnableDiscoveryClient)
   - Integración con Config Server (spring.config.import)
   - Puerto 8081
   - Base de datos H2 en memoria

4. **Customer Service** (`customer-service/`)
   - Estructura de capas completa (controller, service, repository, model, dto, client)
   - Entity `Customer` con campos: id, nombre, documento, correo, saldo
   - CustomerDTO correspondiente
   - CustomerRepository (JpaRepository)
   - CustomerService (esqueleto con TODO)
   - CustomerController (esqueleto con TODO)
   - **ProductServiceClient** (interfaz Feign sin implementar, apunta a "product-service")
   - Integración con Eureka (EnableDiscoveryClient)
   - Integración con Config Server (spring.config.import)
   - Integración con OpenFeign (EnableFeignClients)
   - Puerto 8080
   - Base de datos H2 en memoria

5. **README.md** (actualizado)
   - Descripción de arquitectura
   - Diagrama ASCII de componentes
   - Instrucciones de orden de arranque
   - Instrucciones de ejecución
   - Tabla de tecnologías y puertos
   - Estructura del proyecto

6. **DEVELOPMENT_PLAN.md** (este archivo)
   - Objetivo general
   - Checklist de 7 fases
   - Seguimiento de progreso

---

## 🎬 Próximo Paso Inmediato

**Fase 2: Lógica de Negocio - Product Service**

1. Implementar métodos CRUD en `ProductService`
2. Implementar `ProductRepository` queries si es necesario
3. Implementar endpoints REST en `ProductController`
4. Crear tests unitarios

**Recomendación**: Una vez completada Fase 2, proceder con Fase 3 de forma análoga para Customer Service, y luego enfocarse en la integración Feign (Fase 4).

---

## 📌 Notas Importantes

- **Sin lógica de negocio aún**: Todos los servicios tienen esqueletos con `// TODO`
- **Sin métodos Feign**: ProductServiceClient está vacío, listo para implementar
- **Config Server con placeholder**: La URL de Git remoto debe ser reemplazada manualmente
- **Bases de datos H2**: Se crean en memoria en cada inicio (create-drop)
- **Versiones exactas**: Java 21, Spring Boot 3.5.0, Spring Cloud 2025.0.0

---

## 🔐 Seguridad y Consideraciones

- [ ] Configurar autenticación/autorización (OAuth2, JWT)
- [ ] Implementar circuit breaker en Feign (Resilience4J)
- [ ] Configurar CORS si es necesario
- [ ] Validación de inputs en endpoints
- [ ] Manejo de excepciones global (ControllerAdvice)

---

## 📚 Referencias

- Spring Cloud Documentation: https://spring.io/projects/spring-cloud
- Spring Cloud Config: https://spring.io/projects/spring-cloud-config
- Eureka: https://github.com/Netflix/eureka
- OpenFeign: https://spring.io/projects/spring-cloud-openfeign

---

**Última actualización**: [Timestamp de creación del proyecto]
**Autor**: Copilot (Scaffolding inicial)
