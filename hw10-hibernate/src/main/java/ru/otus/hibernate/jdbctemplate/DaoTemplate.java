package ru.otus.hibernate.jdbctemplate;

import java.sql.SQLException;

public interface DaoTemplate {

    <T> void create(T object);
    <T> void update(T object);
    <T> T load(long id, Class<T> clazz);
    <T> void createOrUpdate(T object);

}
