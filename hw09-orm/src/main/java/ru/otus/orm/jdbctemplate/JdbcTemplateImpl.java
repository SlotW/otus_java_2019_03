package ru.otus.orm.jdbctemplate;

import ru.otus.orm.annotations.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Alexandr Byankin on 22.07.2019
 */
public class JdbcTemplateImpl<T> implements JdbcTemplate<T> {

    private final DbExecutor<T> executor;

    public JdbcTemplateImpl(Connection connection) {
        this.executor = new DbExecutorImpl(connection);
    }

    @Override
    public void create(T object) throws SQLException {
        if(isCorrectObject(object)) {
            Map<Field, String> fieldsValue = getMapFieldValue(object);
            if(fieldsValue.size() > 0){
                String fieldNames = fieldsValue.entrySet().stream()
                        .filter(it->!it.getKey().isAnnotationPresent(Id.class))
                        .map(it->it.getKey().getName())
                        .reduce((x, y)->x + "," + y)
                        .get();
                List<String> values = fieldsValue.entrySet().stream().
                        filter(it->!it.getKey().isAnnotationPresent(Id.class))
                        .map(Map.Entry::getValue)
                        .collect(Collectors.toList());
                String sql = "INSERT INTO " + object.getClass().getSimpleName() + " (" + fieldNames + ") VALUES (" +
                        values.stream().map(x->"?").reduce((x,y)->x + "," + y).get()+ ")";
                executor.insert(sql, values);

            }
        } else {
            System.out.println("Действие create не выполнено");
        }
    }

    @Override
    public void update(T object) throws SQLException {
        if(isCorrectObject(object)) {
            Map<Field, String> fieldValues = getMapFieldValue(object);
            if(fieldValues.size() > 0){
                String setSqlString = fieldValues.entrySet().stream()
                        .filter(it->!it.getKey().isAnnotationPresent(Id.class))
                        .map(it->it.getKey().getName() + "=?")
                        .reduce((x, y)->x + "," + y)
                        .get();
                Field idField = fieldValues.entrySet().stream()
                        .filter(it->it.getKey().isAnnotationPresent(Id.class))
                        .findFirst()
                        .get()
                        .getKey();
                String idValue = fieldValues.get(idField);
                String sql = "UPDATE " + object.getClass().getSimpleName()
                        + " SET " + setSqlString + " WHERE " + idField.getName() + "=?";
                List<String> parameters = Stream.concat(
                        fieldValues.entrySet().stream()
                                .filter(it->!it.getKey().isAnnotationPresent(Id.class))
                                .map(Map.Entry::getValue),
                        Stream.of(idValue)
                ).collect(Collectors.toList());
                executor.update(sql, parameters);
            }
        } else {
            System.out.println("Действие update не выполнено");
        }
    }

    @Override
    public T load(long id, Class clazz) throws SQLException{
        if(!isCorrectEntityClass(clazz)) return null;

        String sql = "select * from "
                + clazz.getSimpleName() + " where "
                + Arrays.stream(clazz.getDeclaredFields())
                    .filter(f->f.isAnnotationPresent(Id.class))
                    .findFirst()
                    .get()
                    .getName()
                + "=?";

        Optional<T> result = executor.select(sql, id, resultSet -> {
            T resultObject = null;
            try {
                if(resultSet.next()){
                    Field[] fields = clazz.getDeclaredFields();
                    Object[] values = Arrays.stream(fields).map(Field::getName).map(name-> {
                        try {
                            return resultSet.getObject(name);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }).toArray();
                    Constructor<T> constructor = clazz.getConstructor(
                            Arrays.stream(fields)
                                    .map(Field::getType)
                                    .toArray(Class[]::new)
                    );
                    resultObject = constructor.newInstance(values);
                }
            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | SQLException | InvocationTargetException e) {
                e.printStackTrace();
            }
            return resultObject;
        });
        return result.orElse(null);
    }

    @Override
    public void createOrUpdate(T object) throws SQLException {
        if(isCorrectObject(object)){
            Map<Field, String> fieldValues = getMapFieldValue(object);
            if(load(
                    Long.parseLong(
                            fieldValues.entrySet().stream()
                                    .filter(it->it.getKey().isAnnotationPresent(Id.class))
                                    .findFirst()
                                    .get()
                                    .getValue()
                    ), object.getClass()
            ) == null) {
                create(object);
            } else {
                update(object);
            }
        } else {
            System.out.println("createOrUpdate не выполнен");
        }
    }


    private boolean isCorrectObject(Object object) {
        if(object == null){
            System.out.println("Передан null");
            return false;
        }
        return isCorrectEntityClass(object.getClass());
    }

    private boolean isCorrectEntityClass(Class clazz){
        if(Arrays.stream(clazz.getDeclaredFields())
                .peek(f->f.setAccessible(true))
                .filter(f->f.isAnnotationPresent(Id.class))
                .count() != 1
        ){
            System.out.println("Не найдено поле с аннотацией Id или таких больше одного");
            return false;
        }
        if(clazz.getDeclaredFields().length < 2){
            System.out.println("Класс содержит только поле с аннотацией Id");
            return false;
        }
        return true;
    }

    private Map<Field, String> getMapFieldValue(Object object){
        Class clazzObject = object.getClass();
        Map<Field, String> fieldValues = new HashMap<>();
        for(Field field : clazzObject.getDeclaredFields()){
            field.setAccessible(true);
            Object fieldObject = null;
            try {
                fieldObject = field.get(object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if(fieldObject != null){
                fieldValues.put(field, fieldObject.toString());
            }
        }
        return fieldValues;
    }

}
