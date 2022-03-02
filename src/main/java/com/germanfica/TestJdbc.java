package com.germanfica;

import com.germanfica.entity.User;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.sql.*;

public class TestJdbc {
	//https://logging.apache.org/log4j/2.x/manual/migration.html
	private static final Logger log = LogManager.getLogger(TestJdbc.class);

	public static void main( String[] args) throws SQLException, ConfigurationException {
		// == application.properties  ==
		FileBasedConfigurationBuilder<PropertiesConfiguration> builder =
				new FileBasedConfigurationBuilder<PropertiesConfiguration>(PropertiesConfiguration.class)
						.configure(new Parameters().properties()
								.setFileName("application.properties")
								.setThrowExceptionOnMissing(true)
								.setListDelimiterHandler(new DefaultListDelimiterHandler(';'))
								.setIncludesAllowed(false));

		PropertiesConfiguration config = builder.getConfiguration();

		// == sql connection ==
		String jdbcUrl = config.getString("datasource.url");
		String user = config.getString("datasource.username");
		String pass = config.getString("datasource.password");

		System.out.println("connecting ...");
		Connection myConn = DriverManager.getConnection(jdbcUrl, user, pass);
		System.out.println("connected");

		// @Query("select s from Submission s where s.assignment.course.id = :courseId")
		//findAll(0 ,2);
		String customQuery = "SELECT first_name FROM user WHERE id=1";

		Statement stmt = myConn.createStatement();
		//CallableStatement cstmt = myConn.prepareCall(customQuery);
		ResultSet resultSet = stmt.executeQuery(customQuery);

		while (resultSet.next()) {
			System.out.println(resultSet.getString("first_name"));;
		}

		myConn.close();
		System.out.println("connection closed. :)");

		// == hibernate  ==
		log.info("Now let's use hibernate!");

		// https://www.programcreek.com/java-api-examples/?api=org.hibernate.boot.registry.StandardServiceRegistryBuilder
		// https://www.programcreek.com/java-api-examples/?code=robeio%2Frobe%2Frobe-master%2Frobe-hibernate%2Fsrc%2Ftest%2Fjava%2Fio%2Frobe%2Fhibernate%2FHibernateUtil.java
		Configuration configuration = new Configuration()
				.setProperty("hibernate.dialect", config.getString("spring.jpa.database-platform"))
				.setProperty("hibernate.connection.driver_class", config.getString("datasource.driverClassName"))
				.setProperty("hibernate.connection.url", config.getString("datasource.url"))
				.setProperty("hibernate.connection.username", config.getString("datasource.username"))
				.setProperty("hibernate.connection.password", config.getString("datasource.password"))
				.addClass(User.class);

		// read configuration and build session factory
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();

		SessionFactory sessionFactory = null;

		try {
			sessionFactory = new MetadataSources(registry)
					.buildMetadata()
					.buildSessionFactory();
		}
		catch (Exception e) {
			StandardServiceRegistryBuilder.destroy(registry);
			log.error("cannot create sessionFactory", e);
			System.exit(1);
		}

		// create session, open transaction and save test entity to db
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		try {
			User testEntity = new User();
			testEntity.setUsername("albedo");
			testEntity.setEmail("albedo@localhost");
			testEntity.setFirstName("albedo");
			testEntity.setLastName("h.");
			testEntity.setPassword("12345678");

			log.info("user: " + testEntity.getFirstName(), testEntity);

			//session.persist(testEntity);
			tx.commit();
		}
		catch (Exception e) {
			tx.rollback();
			log.error("cannot commit transaction", e);
		}
		finally {
			session.close();
		}

		// clean up
		sessionFactory.close();
	}
}
