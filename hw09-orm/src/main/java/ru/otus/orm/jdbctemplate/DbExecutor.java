package ru.otus.orm.jdbctemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * Created by Alexandr Byankin on 22.07.2019
 */
public interface DbExecutor<T> {

    void insert(String sql, List<String> params) throws SQLException;
    void update(String sql, List<String> params) throws SQLException;
    Optional<T> select(String sql, long id, Function<ResultSet, T> rsHandler) throws SQLException;

}
