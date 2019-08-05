package ru.otus.orm.jdbctemplate;

import java.sql.SQLException;

public interface JdbcTemplate<T> {

    void create(T object) throws SQLException;
    void update(T object) throws SQLException;
    T load(long id, Class<T> clazz) throws SQLException;
    void createOrUpdate(T object) throws SQLException;
    boolean isExists(long id, Class clazz) throws SQLException;

}
