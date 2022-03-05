package com.germanfica;

import com.germanfica.entity.User;
import com.germanfica.repository.UserRepositoryImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;

import java.sql.*;
import java.util.LinkedHashSet;
import java.util.Set;

public class TestHibernate {
	//https://logging.apache.org/log4j/2.x/manual/migration.html
	private static final Logger log = LogManager.getLogger(TestHibernate.class);

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

		User delUser = new User();
		delUser.setId(35);

		Set<Integer> ids = new LinkedHashSet();
		ids.add(1);
		ids.add(4);

		Set<User> users = new LinkedHashSet();
		User user1 = new User();
		testEntity.setUsername("albedo16");
		testEntity.setEmail("albedo@localhost");
		testEntity.setFirstName("albedo");
		testEntity.setLastName("h.");
		testEntity.setPassword("12345678");
		User user2 = new User();
		testEntity.setUsername("albedo17");
		testEntity.setEmail("albedo@localhost");
		testEntity.setFirstName("albedo");
		testEntity.setLastName("h.");
		testEntity.setPassword("12345678");

		//userRepository.save(testEntity);
		//userRepository.findById(1);
		//userRepository.delete(delUser);
		//log.info(userRepository.findById(90).get().getFirstName());
		//log.info("Id del username: "+userRepository.findByUsername("guillermina").get().getId());
		//userRepository.findAll().forEach(user -> log.info("username: " + user.getUsername()));
		//userRepository.deleteById(17);
		log.info("Users in the database: " + userRepository.count());
		//userRepository.findAllById(ids).forEach(user -> log.info("Username: " + user.getUsername()));
		//log.info("exists? " + userRepository.existsById(4));
		
		// Not implemented
		userRepository.saveAll(users);
		userRepository.deleteAllById(ids);
		userRepository.deleteAll(users);
		userRepository.deleteAll();
	}
}
