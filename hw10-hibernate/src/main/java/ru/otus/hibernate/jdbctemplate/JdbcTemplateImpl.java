package ru.otus.hibernate.jdbctemplate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Created by Alexandr Byankin on 22.07.2019
 */
public class JdbcTemplateImpl implements JdbcTemplate {

    private final SessionFactory sessionFactory;

    public JdbcTemplateImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public <T> void create(T object) {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.save(object);
            session.getTransaction().commit();
        }
    }

    @Override
    public <T> void update(T object) {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.update(object);
            session.getTransaction().commit();
        }
    }

    @Override
    public <T> T load(long id, Class<T> clazz) {
        T selected;
        try (Session session = sessionFactory.openSession()) {
            selected = session.get(clazz, id);
        }
        return selected;
    }

    @Override
    public <T> void createOrUpdate(T object) {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.saveOrUpdate(object);
            session.getTransaction().commit();
        }
    }

}