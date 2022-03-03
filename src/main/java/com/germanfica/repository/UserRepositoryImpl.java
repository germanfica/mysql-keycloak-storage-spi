package com.germanfica.repository;

import com.germanfica.entity.User;
import com.germanfica.provider.HibernateFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {
    private static final Logger log = LogManager.getLogger(UserRepositoryImpl.class);

    @Override
    public <S extends User> S save(S entity) {
        // create hibernate session factory
        HibernateFactory factory = new HibernateFactory();

        // create session, open transaction and save test entity to db
        Session session = factory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        try {
            log.info("entity: " + entity.toString(), entity);

            session.persist(entity);
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

        return null;
    }

    @Override
    public <S extends User> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<User> findById(Integer integer) {
        Optional<User> opt = Optional.empty();

        // create hibernate session factory
        HibernateFactory factory = new HibernateFactory();

        // create session, open transaction and save test entity to db
        Session session = factory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        try {
            log.info("entity id: " + integer);

            User user = session.get(User.class, integer);
            // https://www.baeldung.com/java-optional
            opt = Optional.ofNullable(user);

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

        return opt;
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public Iterable<User> findAll() {
        return null;
    }

    @Override
    public Iterable<User> findAllById(Iterable<Integer> integers) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void delete(User entity) {
        // create hibernate session factory
        HibernateFactory factory = new HibernateFactory();

        // create session, open transaction and save test entity to db
        Session session = factory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        try {
            log.info("entity: " + entity.toString(), entity);

            session.delete(entity);
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

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends User> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public Optional<User> findByUsername(String username) {
        Optional<User> opt = Optional.empty();

        // create hibernate session factory
        HibernateFactory factory = new HibernateFactory();

        // create session, open transaction and save test entity to db
        Session session = factory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        try {
            log.info("entity username: " + username);

            User user = session.createQuery("select u from User u where u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
            opt = Optional.ofNullable(user);

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

        return opt;
    }
}
