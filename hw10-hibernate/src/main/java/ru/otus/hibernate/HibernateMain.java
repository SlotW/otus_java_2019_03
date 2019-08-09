package ru.otus.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import ru.otus.hibernate.entity.Address;
import ru.otus.hibernate.entity.Phone;
import ru.otus.hibernate.entity.User;
import ru.otus.hibernate.jdbctemplate.JdbcTemplate;
import ru.otus.hibernate.jdbctemplate.JdbcTemplateImpl;
import java.util.List;

/**
 * Created by Alexandr Byankin on 22.07.2019
 */
public class HibernateMain {

    private static SessionFactory sessionFactory;

    public static void main(String... args) {

        configureHibernate();
        JdbcTemplate jdbcTemplate = new JdbcTemplateImpl(sessionFactory);
        Address address1 = new Address();
        address1.setStreet("Не дом и не улица");
        Address address2 = new Address();
        address1.setStreet("Улица вязов");

        Phone phone1 = new Phone();
        phone1.setNumber("8989899999");

        Phone phone2 = new Phone();
        phone2.setNumber("911");

        Phone phone3 = new Phone();
        phone3.setNumber("112");

        User userok = new User();
        userok.setName("Аркадий");

        User user = new User();
        user.setName("Пётр");
        user.setAge(92);
        user.setAddress(address1);
        user.setPhones(List.of(phone1, phone2));

        jdbcTemplate.create(user);
        jdbcTemplate.createOrUpdate(userok);
        System.out.println(jdbcTemplate.load(1, User.class));
        System.out.println(jdbcTemplate.load(5, User.class));

        user.setName("Василий");
        jdbcTemplate.update(user);
        System.out.println(jdbcTemplate.load(1, User.class));
        System.out.println(jdbcTemplate.load(5, User.class));

        userok.setAge(11);
        userok.setAddress(address2);
        userok.setPhones(List.of(phone3));
        jdbcTemplate.createOrUpdate(userok);
        System.out.println(jdbcTemplate.load(1, User.class));
        System.out.println(jdbcTemplate.load(5, User.class));


    }

    private static void configureHibernate(){
        Configuration configuration = new Configuration()
                .configure("hibernate.cfg.xml");

        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();

        Metadata metadata = new MetadataSources(serviceRegistry)
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Phone.class)
                .addAnnotatedClass(Address.class)
                .getMetadataBuilder()
                .build();

        sessionFactory = metadata.buildSessionFactory();
    }
}
