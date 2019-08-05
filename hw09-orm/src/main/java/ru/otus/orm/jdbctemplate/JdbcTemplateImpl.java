package ru.otus.orm.jdbctemplate;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by Alexandr Byankin on 22.07.2019
 */
public class JdbcTemplateImpl<T> implements JdbcTemplate<T> {

    private final DbExecutor<T> executor;
    private final Set<EntityDecomposite> decomposites = new HashSet<>();

    public JdbcTemplateImpl(Connection connection) {
        this.executor = new DbExecutorImpl(connection);
    }

    @Override
    public void create(T object) throws SQLException {
        if (object != null) {
            EntityDecomposite entityDecomposite = findOrCreateEntityDecomposite(object.getClass());
            if(entityDecomposite.correctEntityClass)
                executor.update(entityDecomposite.getInsertSql(), entityDecomposite.getParamsWithoutId(object));
        } else {
            System.out.println("Действие create не выполнено");
        }
    }

    @Override
    public void update(T object) throws SQLException {
        if (object != null) {
            EntityDecomposite entityDecomposite = findOrCreateEntityDecomposite(object.getClass());
            if(entityDecomposite.correctEntityClass) {
                List<Object> params = entityDecomposite.getParamsWithoutId(object);
                params.add(entityDecomposite.getId(object));
                executor.update(entityDecomposite.getUpdateSql(), params);
            }
        } else {
            System.out.println("Действие update не выполнено");
        }
    }

    @Override
    public T load(long id, Class clazz) throws SQLException {
        if(clazz != null){
            EntityDecomposite entityDecomposite = findOrCreateEntityDecomposite(clazz);
            if(entityDecomposite.correctEntityClass){
                Optional<T> result = executor.select(entityDecomposite.getSelectByIdSql(), id, resultSet -> {
                    T resultObject = null;
                    try {
                        if (resultSet.next()) {
                            Object[] values = entityDecomposite.getAllFieldsList().stream()
                                    .map(it-> {
                                        try {
                                            return resultSet.getObject(it.getName());
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                            return null;
                                        }
                                    }).toArray();
                            resultObject = (T) entityDecomposite.getConstructor().newInstance(values);
                        }
                    } catch (IllegalAccessException | InstantiationException | SQLException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    return resultObject;
                });
                return result.orElse(null);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public void createOrUpdate(T object) throws SQLException {
        if(object != null){
            EntityDecomposite entityDecomposite = findOrCreateEntityDecomposite(object.getClass());
            if(entityDecomposite.correctEntityClass){
                if(isExists(entityDecomposite.getId(object), object.getClass())) {
                    update(object);
                } else {
                    create(object);
                }
            }
        } else {
            System.out.println("createOrUpdate не выполнен");
        }
    }

    @Override
    public boolean isExists(long id, Class clazz) throws SQLException {
        if(clazz != null){
            EntityDecomposite entityDecomposite = findOrCreateEntityDecomposite(clazz);
            if(entityDecomposite.correctEntityClass){
                Optional<T> result = executor.select(entityDecomposite.getSelectForExistsSql(), id, resultSet->{
                    try {
                        if(resultSet.next()){
                            return (T) new Object();
                        } else {
                            return null;
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return null;
                    }
                });
                return !result.isEmpty();
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private EntityDecomposite findOrCreateEntityDecomposite(Class clazz){
        EntityDecomposite entityDecomposite = decomposites.stream()
                .filter(it->it.getClazz() == clazz)
                .findFirst()
                .orElse(new EntityDecomposite(clazz));
        decomposites.add(entityDecomposite);
        return entityDecomposite;
    }
}