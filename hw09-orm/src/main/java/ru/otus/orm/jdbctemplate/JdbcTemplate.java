package ru.otus.orm.jdbctemplate;

import ru.otus.orm.annotations.Id;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Alexandr Byankin on 22.07.2019
 */
public class JdbcTemplate<T> {

    private final DbExecutor executor;

    public JdbcTemplate(Connection connection) {
        this.executor = new DbExecutorImpl(connection);
    }

    public void create(T object) throws SQLException {
        if(isValidObject(object)) {
            String sql = "INSERT INTO ? () VALUES ()";
            List<String> parameters = new ArrayList<>();
            executor.insert(sql, parameters);
        } else {
            System.out.println("Действие create не выполнено");
        }
    }

    public void update(T object){

    }

    public T load(long id, Class<T> clazz){
        return null;
    }

    private boolean isValidObject(Object object){
        if(object == null){
            System.out.println("null");
            return false;
        }
        if(Arrays.stream(object.getClass().getDeclaredFields()).peek(f->f.setAccessible(true)).filter(f->f.isAnnotationPresent(Id.class)).count() != 1){
            System.out.println("Не найдено поле с аннотацией id или таких больше одного");
            return false;
        }
        return true;
    }

}
