package ru.otus.orm.jdbctemplate;

import ru.otus.orm.annotations.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Alexandr Byankin on 04.08.2019
 */
public class EntityDecomposite {

    private Class clazz;
    private Field idField;
    private Constructor constructor;
    private List<Field> fieldsListWithoutId = new ArrayList<>();
    private List<Field> allFieldsList = new ArrayList<>();
    private String insertSql;
    private String updateSql;
    private String selectByIdSql;
    private String selectForExistsSql;
    public boolean correctEntityClass = false;

    private EntityDecomposite() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public EntityDecomposite(Class clazz){
        if(clazz != null && !clazz.equals(this.clazz)){
            correctEntityClass = true;
            this.clazz = clazz;
            allFieldsList = Arrays.asList(clazz.getDeclaredFields());
            findIdField();
            findFieldsWithoutId();
            try {
                constructor = clazz.getConstructor(allFieldsList.stream().map(Field::getType).toArray(Class[]::new));
            } catch (NoSuchMethodException e) {
                correctEntityClass = false;
                e.printStackTrace();
            }
            if(correctEntityClass){
                generateSelectByIdSql();
                generateInsertSql();
                generateUpdateSql();
                generateSelectForExistsSql();
            }
        }
    }

    public Class getClazz(){
        return clazz;
    }

    public Constructor getConstructor() {
        return constructor;
    }

    public List<Field> getAllFieldsList() {
        return allFieldsList;
    }

    public String getInsertSql() {
        return insertSql;
    }

    public String getUpdateSql() {
        return updateSql;
    }

    public String getSelectByIdSql() {
        return selectByIdSql;
    }

    public String getSelectForExistsSql(){
        return selectForExistsSql;
    }

    private void findIdField(){
        Field[] idFields = allFieldsList.stream()
                .peek(f->f.setAccessible(true))
                .filter(f->f.isAnnotationPresent(Id.class))
                .toArray(Field[]::new);
        if(idFields.length == 1){
            idField = idFields[0];
        } else {
            correctEntityClass = false;
        }
    }

    private void findFieldsWithoutId(){
        allFieldsList = Arrays.asList(clazz.getDeclaredFields());
        fieldsListWithoutId = Arrays.stream(clazz.getDeclaredFields())
                .filter(it -> !it.isAnnotationPresent(Id.class))
                .collect(Collectors.toList());
        if(fieldsListWithoutId.size() == 0) correctEntityClass = false;
    }

    private void generateInsertSql(){
        String fieldNames = fieldsListWithoutId.stream()
                .map(Field::getName)
                .reduce((x, y)->x + "," + y)
                .orElse("");
        insertSql = "INSERT INTO " + clazz.getSimpleName() + " (" + fieldNames + ") VALUES (" +
                fieldsListWithoutId.stream().map(x->"?").reduce((x,y)->x + "," + y).orElse("")+ ")";
    }

    private void generateUpdateSql(){
        String setSqlString = fieldsListWithoutId.stream()
                .map(it -> it.getName() + "=?")
                .reduce((x, y) -> x + "," + y)
                .orElse("");
        updateSql = "UPDATE " + clazz.getSimpleName()
                + " SET " + setSqlString + " WHERE " + idField.getName() + "=?";
    }

    private void generateSelectByIdSql(){
        selectByIdSql = "SELECT * FROM "
                + clazz.getSimpleName() + " WHERE "
                + idField.getName()
                + "=?";
    }

    private void generateSelectForExistsSql(){
        selectForExistsSql = "SELECT 1 FROM "
                + clazz.getSimpleName() + " WHERE "
                + idField.getName()
                + "=?";
    }

    public List<Object> getParamsWithoutId(Object object){
        return fieldsListWithoutId.stream().peek(it->it.setAccessible(true)).map(it-> {
            try {
                return it.get(object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return null;
            }
        }).collect(Collectors.toList());
    }

    public long getId(Object object){
        idField.setAccessible(true);
        long result = 0;
        try {
            result = (Long)idField.get(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean equals(Object object){
        return this.clazz == object.getClass();
    }

}
