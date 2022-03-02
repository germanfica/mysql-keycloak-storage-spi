# A custom Keycloak User Storage Provider

## Configure application properties

Create `src/main/resources/application.properties`

```xml
datasource.username = your_username
datasource.password = your_password
datasource.url = jdbc:mysql://x.x.x.x:3306/your_db_name?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&zeroDateTimeBehavior=convertToNull
```
