# TTSIKU

Hệ thống quản lý công việc xây dựng bằng **Spring Boot**, sử dụng **SQL Server**, **Spring Security + JWT** và **Swagger UI** để tài liệu hóa và kiểm thử API.

---

## 1. Công nghệ sử dụng

- Java 17
- Spring Boot
- Spring Security
- Spring Data JPA
- SQL Server
- JWT
- Swagger / OpenAPI
- Maven

---

## 2. Cấu hình môi trường

Project tách cấu hình theo profile:

- `application.properties`: cấu hình dùng chung
- `application-dev.properties`: cấu hình môi trường phát triển
- `application-prod.properties`: cấu hình môi trường production

### Ví dụ `application-dev.properties`

```properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=tuan2;encrypt=true;trustServerCertificate=true;
spring.datasource.username=sa
spring.datasource.password=123

logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE

spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

springdoc.swagger-ui.path=/swagger-ui.html
springdoc.api-docs.path=/v3/api-docs
springdoc.swagger-ui.tryItOutEnabled=true

jwt.secret-key=aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
jwt.time-life=1000000