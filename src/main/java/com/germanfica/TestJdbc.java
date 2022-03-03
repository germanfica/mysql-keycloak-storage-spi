package com.germanfica;

import com.germanfica.entity.User;
import com.germanfica.provider.HibernateFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.*;

public class TestJdbc {
	//https://logging.apache.org/log4j/2.x/manual/migration.html
	private static final Logger log = LogManager.getLogger(TestJdbc.class);

	public static void main( String[] args) throws SQLException, HibernateException {
		// == hibernate  ==
		log.info("Now let's use hibernate!");

		// create hibernate session factory
		HibernateFactory factory = new HibernateFactory();

		// create session, open transaction and save test entity to db
		Session session = factory.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();

		addUser(session, tx);

		// clean up
		factory.close();
	}

	private static void addUser(Session session, Transaction tx) {
		try {
			User testEntity = new User();
			testEntity.setUsername("albedo5");
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
	}
}
