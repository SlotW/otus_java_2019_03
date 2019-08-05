package ru.otus.orm.jdbctemplate;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * Created by Alexandr Byankin on 22.07.2019
 */
public class DbExecutorImpl<T> implements DbExecutor<T> {

    private final Connection connection;

    public DbExecutorImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void update(String sql, List<Object> params) throws SQLException {
        Savepoint savePoint = connection.setSavepoint("Savepoint for " + sql);
        try (PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            for(int idx = 0; idx < params.size(); idx++) {
                pst.setObject(idx + 1, params.get(idx));
            }
            pst.executeUpdate();
        } catch (SQLException ex) {
            connection.rollback(savePoint);
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public Optional<T> select(String sql, long id, Function<ResultSet, T> rsHandler) throws SQLException {
        try (PreparedStatement pst = this.connection.prepareStatement(sql)) {
            pst.setLong(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                return Optional.ofNullable(rsHandler.apply(rs));
            }
        }
    }

}
