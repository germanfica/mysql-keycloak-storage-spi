package com.germanfica.repository;

import com.germanfica.entity.User;
import com.germanfica.provider.HibernateFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.*;

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
        //Set<User> users = new LinkedHashSet();
        //https://www.geeksforgeeks.org/initializing-a-list-in-java/
        List<User> users = new LinkedList();

        // create hibernate session factory
        HibernateFactory factory = new HibernateFactory();

        // create session, open transaction and save test entity to db
        Session session = factory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        try {
            log.info("findAll() " + User.class);
            users = session.createQuery("SELECT u FROM User u", User.class).getResultList();

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

        return users;
    }

    @Override
    public Iterable<User> findAllById(Iterable<Integer> integers) {
        List<User> users = new LinkedList();

        // create hibernate session factory
        HibernateFactory factory = new HibernateFactory();

        // create session, open transaction and save test entity to db
        Session session = factory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        try {
            log.info("findAll() " + User.class);
            // https://thorben-janssen.com/fetch-multiple-entities-id-hibernate/
            users = session.createQuery("SELECT u FROM User u WHERE u.id IN :ids", User.class)
                    .setParameter("ids", integers)
                    .getResultList();

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

        return users;
    }

    @Override
    public long count() {
        long countNumber = -1;
        // create hibernate session factory
        HibernateFactory factory = new HibernateFactory();

        // create session, open transaction and save test entity to db
        Session session = factory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        try {
            log.info("count() " + User.class);
            //https://stackoverflow.com/questions/17383697/how-to-write-a-query-in-hibernate-for-count
            countNumber = (long) session.createQuery("SELECT count(*) FROM User").uniqueResult();

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

        return countNumber;
    }

    @Override
    public void deleteById(Integer integer) {
        // create hibernate session factory
        HibernateFactory factory = new HibernateFactory();

        // create session, open transaction and save test entity to db
        Session session = factory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        try {
            log.info("deleting user with id: " + integer);

            // https://stackoverflow.com/questions/28439141/update-delete-queries-cannot-be-typed-jpa
            // Be careful here, don't forget to add the "executeUpdate()" after the "setParameter()"
            // executeUpdate() for insert, delete and update.
            // getSingleResult(), getResultList() for get.
            // Pseudo example: delete from user where id=?
            // An HQL DELETE statement
            session.createQuery("DELETE User WHERE id = :id")
                    .setParameter("id", integer).executeUpdate();

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
