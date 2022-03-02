# A custom Keycloak User Storage Provider

## Configure application properties

Create `src/main/resources/application.properties`

```xml
datasource.username = your_username
datasource.password = your_password
datasource.url = jdbc:mysql://x.x.x.x:3306/your_db_name?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&zeroDateTimeBehavior=convertToNull
```
## Maven dependencies

Apache Maven Compiler Plugin
```xml
<!-- https://mvnrepository.com/artifact/org.apache.maven.plugins/maven-compiler-plugin -->
<dependency>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <version>3.10.0</version>
</dependency>
```

Keycloak Server SPI
```xml
<!-- https://mvnrepository.com/artifact/org.keycloak/keycloak-server-spi -->
<dependency>
    <groupId>org.keycloak</groupId>
    <artifactId>keycloak-server-spi</artifactId>
    <version>17.0.0</version>
    <scope>provided</scope>
</dependency>
```

Hibernate
```xml
<!-- https://mvnrepository.com/artifact/org.hibernate/hibernate-core -->
<dependency>
    <groupId>org.hibernate</groupId>
    <artifactId>hibernate-core</artifactId>
    <version>5.6.5.Final</version>
</dependency>
```
Check the [Hibernate official documentation](https://hibernate.org/orm/documentation/getting-started/).
