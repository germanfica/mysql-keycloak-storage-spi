package com.germanfica.repository;

import com.germanfica.entity.User;
import com.germanfica.provider.HibernateFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserRepository {
    private static final Logger log = LogManager.getLogger(UserRepository.class);

    public void save(User user) {
        // create hibernate session factory
        HibernateFactory factory = new HibernateFactory();

        // create session, open transaction and save test entity to db
        Session session = factory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        try {
            log.info("user: " + user.getFirstName(), user.toString());

            session.persist(user);
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
        factory.close();
    }
}
