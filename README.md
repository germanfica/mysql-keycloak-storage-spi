# A custom Keycloak User Storage Provider

## How to build a jar file that includes its dependencies?

You should include your project dependencies in your `.jar` file. Basically you need a fat `.jar` file. This is necessary otherwise your `.jar` will not be able to make use of those dependencies it needs and will give an error.

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
- https://stackoverflow.com/a/16222971

## Send .jar in to a docker container

``` bash
docker cp target/user-storage-spi-0.0.1-SNAPSHOT-jar-with-dependencies.jar container_id:/opt/jboss/keycloak/standalone/deployments/user-storage-spi-0.0.1-SNAPSHOT-jar-with-dependencies.jar
```

## Configurations

Create `src/main/resources/hibernate.cfg.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE hibernate-configuration PUBLIC "-//Hibernate/Hibernate Configuration DTD 3.0//EN" "http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">
<hibernate-configuration>
    <session-factory>
        <!-- Database connection settings -->
        <property name="connection.driver_class">com.mysql.cj.jdbc.Driver</property>
        <property name="connection.url">jdbc:mysql://x.x.x.x:3306/YOUR_DB_NAME</property>
        <property name="connection.username">YOUR_DB_USERNAME</property>
        <property name="connection.password">YOUR_DB_PASSWORD</property>
        <!-- SQL dialect -->
        <property name="dialect">org.hibernate.dialect.MySQL8Dialect</property>
        <!-- Hibernate settings -->
        <property name="hibernate.show_sql">true</property>
        <property name="hibernate.c3p0.min_size">5</property>
        <property name="hibernate.c3p0.max_size">20</property>
        <property name="hibernate.c3p0.timeout">1800</property>
        <property name="hibernate.c3p0.max_statements">50</property>
        <property name="hibernate.c3p0.acquire_increment">3</property>
        <property name="hibernate.c3p0.idle_test_period">3000</property>
        <!-- <property name="hibernate.c3p0.initialPoolSize">5</property> -->
        <property name="hibernate.c3p0.validate">true</property>

        <!-- List all the mapping documents we're using -->
        <!-- <mapping resource="example01/Teacher.hbm.xml"/> -->
        <!-- <mapping class="example01.Teacher"/> -->
        <mapping class="com.germanfica.entity.Role"/>
        <mapping class="com.germanfica.entity.User"/>
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
