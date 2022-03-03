package com.germanfica;

import com.germanfica.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
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

	public static void main( String[] args) throws SQLException, HibernateException {
		// == hibernate  ==
		log.info("Now let's use hibernate!");

		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
				.configure().build();

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
			testEntity.setUsername("albedo3");
			testEntity.setEmail("albedo@localhost");
			testEntity.setFirstName("albedo");
			testEntity.setLastName("h.");
			testEntity.setPassword("12345678");

			log.info("user: " + testEntity.getFirstName(), testEntity);

			session.persist(testEntity);
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
