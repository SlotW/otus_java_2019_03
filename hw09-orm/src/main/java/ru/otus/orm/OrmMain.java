package ru.otus.orm;

import ru.otus.orm.dao.Account;
import ru.otus.orm.dao.User;
import ru.otus.orm.jdbctemplate.JdbcTemplate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Alexandr Byankin on 22.07.2019
 */
public class OrmMain {

    private static final String URL = "jdbc:h2:mem:";

    public static void main(String... args) throws SQLException {
        Connection connection = getConnection();
        createTable(connection);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(connection);

        getPoolAccaunts().forEach(x->{
            try {
                jdbcTemplate.create(x);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        System.out.println(jdbcTemplate.load(2, User.class));

        jdbcTemplate.update(new User(2, "Имя сменил", 111));

        System.out.println(jdbcTemplate.load(2, User.class));

        /*getPoolAccaunts().forEach(x-> {
            try {
                jdbcTemplate.create(x);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        System.out.println(jdbcTemplate.load(2, User.class));*/

    }



    private static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(URL);
        connection.setAutoCommit(false);
        return connection;
    }

    private static void createTable(Connection connection) throws SQLException {
        try (PreparedStatement pst = connection.prepareStatement("create table user(id bigint(20) NOT NULL auto_increment, name varchar(255), age int(3))")) {
            pst.executeUpdate();
        }
        try (PreparedStatement pst = connection.prepareStatement("create table user(no bigint(20) NOT NULL auto_increment, type varchar(255), rest number)")) {
            pst.executeUpdate();
        }
    }

    private static List<User> getPoolUsers(){
        List<User> users = new ArrayList<>();
        users.add(new User(0, "Петров", 25));
        users.add(new User(0, "Кощей", 99));
        users.add(new User(0, "Хто-то", 13));
        return users;
    }

    private static List<Account> getPoolAccaunts(){
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account(0, "Temp", 10));
        accounts.add(new Account(0, "Temp", 11));
        accounts.add(new Account(0, "Technical", 100));
        return accounts;
    }

}
