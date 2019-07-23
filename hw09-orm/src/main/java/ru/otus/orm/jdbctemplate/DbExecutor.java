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

    public void insert(String sql, List<String> params) throws SQLException;
    public void update(String sql, List<String> params) throws SQLException;
    public Optional<T> select(String sql, Function<ResultSet, T> rsHandler) throws SQLException;

}
