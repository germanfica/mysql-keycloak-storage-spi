# A custom Keycloak User Storage Provider

## How to build a jar file that includes its dependencies?

You should include your project dependencies in your `.jar` file. This is necessary otherwise your `.jar` will not be able to make use of those dependencies it needs and will give an error.

Here is one of the ways to do it. You should add the following plugin to your `pom.xml` file:

```xml
<!-- https://mvnrepository.com/artifact/org.apache.maven.plugins/maven-assembly-plugin -->
<plugin>
	<groupId>org.apache.maven.plugins</groupId>
	<artifactId>maven-assembly-plugin</artifactId>
	<version>3.3.0</version>
	<executions>
		<execution>
			<id>make-assembly</id>
			<phase>package</phase>
			<goals>
				<goal>single</goal>
			</goals>
		</execution>
	</executions>
	<configuration>
		<descriptorRefs>
			<descriptorRef>jar-with-dependencies</descriptorRef>
		</descriptorRefs>
	</configuration>
</plugin>
```

Maven Assembly Plugin will include all the `pom.xml` dependencies to your `.jar` file.

Then you must run the following maven goal:

```
mvn clean install package assembly:single
```

Alternatively you can also run:

```
mvn clean compile assembly:single
```

Once you have generated the `.jar` with all its dependencies you must paste it in the `/standalone/deployments` Keycloak directory. And that's it!

Source:
- https://stackoverflow.com/a/1729094
- https://stackoverflow.com/a/574650

## Configurations

Create `src/main/resources/application.properties`

```xml
datasource.username = your_username
datasource.password = your_password
datasource.url = jdbc:mysql://x.x.x.x:3306/your_db_name?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&zeroDateTimeBehavior=convertToNull
```

Create `src/main/resources/hibernate.cfg.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE hibernate-configuration PUBLIC "-//Hibernate/Hibernate Configuration DTD 3.0//EN" "http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">
<hibernate-configuration>
    <session-factory>
        <property name="connection.driver_class">com.mysql.cj.jdbc.Driver</property>
        <property name="connection.url">jdbc:mysql://localhost/hibernate1</property>
        <property name="connection.username">hibernate1</property>
        <property name="connection.password">hibernate1</property>
        <property name="dialect">org.hibernate.dialect.MySQLDialect</property>
        <property name="hibernate.show_sql">true</property>

        <mapping resource="example01/Teacher.hbm.xml"/>
        <mapping class="example01.Teacher"/>
    </session-factory>
</hibernate-configuration>
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
