package com.germanfica;

import com.germanfica.entity.User;
import com.germanfica.repository.UserRepositoryImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;

import java.sql.*;

public class TestJdbc {
	//https://logging.apache.org/log4j/2.x/manual/migration.html
	private static final Logger log = LogManager.getLogger(TestJdbc.class);

	public static void main( String[] args) throws SQLException, HibernateException {
		// == hibernate  ==
		log.info("Now let's use hibernate!");

		UserRepositoryImpl userRepository = new UserRepositoryImpl();

		User testEntity = new User();
		testEntity.setUsername("albedo7");
		testEntity.setEmail("albedo@localhost");
		testEntity.setFirstName("albedo");
		testEntity.setLastName("h.");
		testEntity.setPassword("12345678");

		userRepository.save(testEntity);
		//userRepository.findById(1);
	}
}
